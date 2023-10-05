package com.demo.accounts.controller.cucumber;

import com.demo.accounts.model.Account;
import com.demo.accounts.model.ApiErrorResponse;
import com.demo.accounts.repository.AccountRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountApiControllerStepDefinitions {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<?> latestResponse;

    @Autowired
    private AccountRepository accountRepository;

    String createUri(int port, String path) {
       return "http://localhost:" + port + path;
    }

    void saveAccount(Account account) {
        accountRepository.save(account);
    }

    //=======create account========

    @When("^the client calls POST (.*) with request body$")
    public void the_client_calls_post_accounts_with_below_request_body(String path, DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        String uri = createUri(port, path);
        dataTable.asList(Account.class).forEach((account) -> {
            ResponseEntity<Account> response = restTemplate.postForEntity(uri, (Account)account, Account.class);

            latestResponse = response;

            assertNotNull(response.getBody());
        });
    }



    @Then("the client recieves status code of {int}")
    public void the_client_recieves_status_code_of(int statusCode) {
        assertEquals(statusCode, latestResponse.getStatusCodeValue());
    }
    @Then("the client recieves the newly created account")
    public void the_client_recieves_the_newly_created_account(DataTable dataTable) {
        dataTable.asList(Account.class).forEach((account) -> {
            Account expected = (Account) account;
            Account actual = (Account) latestResponse.getBody();

            assertEquals(expected.toString(), actual.toString());
        });
    }

    //=======get account by id===========
    @Given("the collection of accounts")
    public void the_collection_of_accounts(DataTable dataTable) {
        dataTable.asList(Account.class).forEach((account) -> {
            saveAccount((Account) account);
        });
    }

    @When("^normal flow, the client calls GET (.*)$")
    public void the_client_calls_get_accounts(String path) {
        String uri = createUri(port, path);
        ResponseEntity<Account> response = restTemplate.getForEntity(uri, Account.class);
        latestResponse = response;

        assertNotNull(response.getBody());
    }

    @Then("the client recieves the account details")
    public void the_client_recieves_the_account_details(DataTable dataTable) {
        dataTable.asList(Account.class).forEach((account) -> {
            Account expected = (Account) account;
            Account actual = (Account) latestResponse.getBody();

            assertEquals(expected.toString(), actual.toString());
        });
    }

    @When("^error flow, the client calls GET (.*)$")
    public void error_flow_the_client_calls_get_accounts(String path) {
        String uri = createUri(port, path);

        ResponseEntity<ApiErrorResponse> response = restTemplate.getForEntity(uri, ApiErrorResponse.class);
        latestResponse = response;

        assertNotNull(response.getBody());
    }

    @Then("the client recieves the error response")
    public void the_client_recieves_the_error_response(DataTable dataTable) {

        dataTable.asList(ApiErrorResponse.class).forEach((errorResponse) -> {
            ApiErrorResponse expected = (ApiErrorResponse) errorResponse;
            ApiErrorResponse actual = (ApiErrorResponse) latestResponse.getBody();

           assertEquals(expected.toString(), actual.toString());
        });
    }

    //=======get all accounts present in db========

    @When("^normal flow, the client calls to retrieve all accounts, GET (.*)$")
    public void normal_flow_the_client_calls_to_retrieve_all_accounts_get_api_accounts(String path) {
        String uri = createUri(port, path);
        ResponseEntity<Account[]> response = restTemplate.getForEntity(uri, Account[].class);

        latestResponse = response;

        assertNotNull(response.getBody());
    }

    @Then("the client recieves the all accounts present in db")
    public void the_client_recieves_the_all_accounts(DataTable dataTable) {
        List<Object>  expectedList = dataTable.asList(Account.class);

        Account[] accountArray = (Account[]) latestResponse.getBody();
        List<Account> actualList = Arrays.asList(accountArray);

        int i = 0;
        for (Object expectedEle : expectedList) {
            Account expected = (Account) expectedEle;
            Account actual = actualList.get(i);

            assertEquals(expected.toString(), actual.toString());

            i++;
        }
    }

    @Given("the account table is empty")
    public void the_account_table_is_empty() {
        accountRepository.deleteAll();
    }

    @When("^error flow, the client calls to retrieve all accounts, GET (.*)$")
    public void error_flow_the_client_calls_to_retrieve_all_accounts_get_api_accounts(String path) {
        String uri = createUri(port, path);
        ResponseEntity<ApiErrorResponse> response = restTemplate.getForEntity(uri, ApiErrorResponse.class);

        latestResponse = response;

        assertNotNull(response.getBody());
    }

    @When("^normal flow, the client calls PUT (.*) with request body$")
    public void normal_flow_the_client_calls_put_api_accounts_with_request_body(String path, DataTable dataTable) {
        String uri = createUri(port, path);
        dataTable.asList(Account.class).forEach((account) -> {
            HttpEntity entity = new HttpEntity((Account) account);
            ResponseEntity<Account> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Account.class);

            latestResponse = response;

            assertNotNull(response.getBody());
        });
    }

    //=======update account by id========
    @Then("the client recieves the updated account details")
    public void the_client_recieves_the_updated_account_details(DataTable dataTable) {
        dataTable.asList(Account.class).forEach((account) -> {
            Account expected = (Account) account;
            Account actual = (Account) latestResponse.getBody();

            assertEquals(expected.toString(), actual.toString());
        });
    }


    @When("^error flow, the client calls PUT (.*)$")
    public void error_flow_the_client_calls_put_api_accounts(String path, DataTable dataTable) {
        String uri = createUri(port, path);
        dataTable.asList(Account.class).forEach((account) -> {
            HttpEntity entity = new HttpEntity((Account) account);
            ResponseEntity<ApiErrorResponse> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, ApiErrorResponse.class);

            latestResponse = response;

            assertNotNull(response.getBody());
        });
    }


    //=======delete account by id========
    @When("^normal flow, the client calls DELETE (.*)$")
    public void normal_flow_the_client_calls_delete_api_accounts(String path) {
        String uri = createUri(port, path);

        HttpEntity entity = new HttpEntity("");
        ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, Void.class);

        latestResponse = response;
    }

    @When("^error flow, the client calls DELETE (.*)$")
    public void error_flow_the_client_calls_delete_api_accounts(String path) {
        String uri = createUri(port, path);

        HttpEntity entity = new HttpEntity("");
        ResponseEntity<ApiErrorResponse> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, ApiErrorResponse.class);

        latestResponse = response;

        assertNotNull(response.getBody());
    }

}
