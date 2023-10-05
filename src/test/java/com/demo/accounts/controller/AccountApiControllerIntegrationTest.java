package com.demo.accounts.controller;

import com.demo.accounts.model.Account;
import com.demo.accounts.model.ApiErrorResponse;
import com.demo.accounts.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountApiControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUri;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Account testAccount;

    @BeforeEach
    void init() {
        baseUri = "http://localhost:" + port + "/api/accounts";
    }

    @AfterEach
    void deleteAccountTableData() {
        System.err.println("======Deleting=====");
        accountRepository.deleteAll();
    }

    @BeforeAll
    static void saveAccountsIntoDb() {
        testAccount = new Account();
        testAccount.setAccountHolderName("test");
        testAccount.setAccountName("saving");
        testAccount.setCountry("IND");
        testAccount.setCurrency("rupees");
        testAccount.setIfsc("33333");
        testAccount.setBalance(new BigDecimal(5000.00));

    }

    @Test
    void testCreateAccount() {
        System.err.println("=====testCreateAccount()=====");
        ResponseEntity<Account> response = testRestTemplate.postForEntity(baseUri, testAccount, Account.class);

        Account actual = response.getBody();
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(testAccount.getAccountHolderName(), actual.getAccountHolderName());
    }

    @Sql({"classpath:data.sql"})
    @Test
    void testGetAccount() {
        System.err.println("=====testGetAccount()=====");
        System.err.println(accountRepository.findAll());
        String uri = baseUri + "/1";
        ResponseEntity<Account> response = testRestTemplate.getForEntity(uri, Account.class);

        Account actual = response.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(1, actual.getAccountId());
        assertEquals("name1", actual.getAccountHolderName());
    }

    @Test
    void testGetAccountForInvalidAccountId() {
        System.err.println("=====testGetAccountForInvalidAccountId()=====");

        String uri = baseUri + "/1";

        ResponseEntity<ApiErrorResponse> response = testRestTemplate.getForEntity(uri, ApiErrorResponse.class);

        ApiErrorResponse actual = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertEquals("Account not found in db with given account id = 1", actual.getMessage());
        assertEquals("/api/accounts/1", actual.getPath());
    }

    @Test
    @Sql({"classpath:data.sql"})
    void testGetAccounts() {
        System.err.println("=====testGetAccounts()=====");

        ResponseEntity<Account[]> response = testRestTemplate.getForEntity(baseUri, Account[].class);
        List<Account> actual = Arrays.asList(response.getBody());

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(2, actual.size());
    }

    @Test
    void testGetAccountsForEmptyTable() {
        System.err.println("=====testGetAccountsForEmptyTable()=====");

        ResponseEntity<ApiErrorResponse> response = testRestTemplate.getForEntity(baseUri, ApiErrorResponse.class);

        ApiErrorResponse actual = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertEquals("There is no accounts present in db.", actual.getMessage());
        assertEquals("/api/accounts", actual.getPath());
    }

    @Test
    @Sql({"classpath:data.sql"})
    void testUpdateAccount() {
        System.err.println("=====testUpdateAccount()=====");

        testAccount.setAccountId(1);

        String uri = baseUri + "/1";
        HttpEntity entity = new HttpEntity(testAccount);
        ResponseEntity<Account> response = testRestTemplate.exchange(uri, HttpMethod.PUT, entity, Account.class);

        Account actual = response.getBody();
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(testAccount.getAccountId(), actual.getAccountId());
        assertEquals(testAccount.getAccountHolderName(), actual.getAccountHolderName());
        assertEquals(testAccount.getIfsc(), actual.getIfsc());
        assertEquals(testAccount.getBalance(), actual.getBalance());
    }

    @Test
    void testUpdateAccountForInvalidAccountId() {
        System.err.println("=====testUpdateAccountForInvalidAccountId()=====");

        testAccount.setAccountId(1);

        String uri = baseUri + "/1";
        HttpEntity entity = new HttpEntity(testAccount);
        ResponseEntity<ApiErrorResponse> response = testRestTemplate.exchange(uri, HttpMethod.PUT, entity, ApiErrorResponse.class);

        ApiErrorResponse actual = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertEquals("Account not found in db with given account id = 1", actual.getMessage());
        assertEquals("/api/accounts/1", actual.getPath());
    }

    @Test
    @Sql({"classpath:data.sql"})
    void testDeleteAccount() {
        System.err.println("=====testDeleteAccount()=====");

        String uri = baseUri + "/1";

        HttpEntity entity = new HttpEntity("");
        ResponseEntity<Void> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, entity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
    }

    @Test
    void testDeleteAccountForInvalidAccount() {
        System.err.println("=====testDeleteAccountForInvalidAccount()=====");

        String uri = baseUri + "/1";

        HttpEntity entity = new HttpEntity("");
        ResponseEntity<ApiErrorResponse> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, entity, ApiErrorResponse.class);

        ApiErrorResponse actual = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertEquals("Account not found in db with given account id = 1", actual.getMessage());
        assertEquals("/api/accounts/1", actual.getPath());
    }
}
