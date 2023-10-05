package com.demo.accounts.controller;

import com.demo.accounts.exception.AccountDetailsNotFoundException;
import com.demo.accounts.model.Account;
import com.demo.accounts.model.ApiErrorResponse;
import com.demo.accounts.service.AccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountApiControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountApiController testClass;

    private static Account account1;

    @BeforeAll
    static void init() {
        account1 = new Account();
        account1.setAccountId(1);
        account1.setAccountHolderName("madhu");
        account1.setAccountName("saving");
        account1.setCountry("IND");
        account1.setCurrency("rupees");
        account1.setBalance(new BigDecimal(10000.00));
        account1.setIfsc("12345");
    }

    @Test
    void testAddAccount() {
        when(accountService.createAccount(any(Account.class))).thenReturn(account1);

        ResponseEntity<Account> actual = testClass.addAccount(account1);

        assertEquals(account1.getAccountHolderName(), actual.getBody().getAccountHolderName());
        assertEquals(HttpStatus.CREATED.value(), actual.getStatusCodeValue());

        verify(accountService, times(1)).createAccount(any(Account.class));
    }

    @Test
    void testDeleteAccount() {

        ResponseEntity<Void> actual = testClass.deleteAccount(1);

        assertEquals(HttpStatus.NO_CONTENT.value(), actual.getStatusCodeValue());

        verify(accountService, times(1)).deleteAccountById(anyInt());
    }

    @Test
    void testGetAccount() {
        when(accountService.fetchAccountById(eq(1))).thenReturn(account1);

        ResponseEntity<Account> actual = testClass.getAccount(1);

        assertEquals(HttpStatus.OK.value(), actual.getStatusCodeValue());
        assertEquals(account1.getAccountHolderName(), actual.getBody().getAccountHolderName());

        verify(accountService, times(1)).fetchAccountById(eq(1));
    }

    @Test
    void testGetAccounts() {
        List<Account> accounts = Arrays.asList(account1);

        when(accountService.fetchAllAccounts()).thenReturn(accounts);

        ResponseEntity<List<Account>> actual = testClass.getAccounts();

        assertEquals(accounts.size(), actual.getBody().size());
        assertEquals(HttpStatus.OK.value(), actual.getStatusCodeValue());

        verify(accountService, times(1)).fetchAllAccounts();
    }

    @Test
    void testUpdateAccount() {
        when(accountService.updateAccountById(eq(1), any(Account.class))).thenReturn(account1);

        ResponseEntity<Account> actual = testClass.updateAccount(1, account1);

        assertEquals(account1.getAccountHolderName(), actual.getBody().getAccountHolderName());
        assertEquals(HttpStatus.OK.value(), actual.getStatusCodeValue());

        verify(accountService, times(1)).updateAccountById(eq(1), any(Account.class));
    }

    @Test
    void testAccountDetailsNotFoundExceptionHandler() {
        AccountDetailsNotFoundException ex = new AccountDetailsNotFoundException("example");
        final HttpServletRequest req = mock(HttpServletRequest.class);
        ResponseEntity<ApiErrorResponse> actual = testClass.accountDetailsNotFoundExceptionHandler(ex, req);

        assertEquals(HttpStatus.NOT_FOUND.value(), actual.getStatusCodeValue());
        assertEquals(ex.getMessage(), actual.getBody().getMessage());

        verify(req).getRequestURI();
    }
}