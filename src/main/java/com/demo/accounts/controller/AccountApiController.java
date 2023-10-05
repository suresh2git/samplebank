package com.demo.accounts.controller;

import com.demo.accounts.exception.AccountDetailsNotFoundException;
import com.demo.accounts.model.Account;
import com.demo.accounts.model.ApiErrorResponse;
import com.demo.accounts.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AccountApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<Account> addAccount(Account account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteAccount(Integer accountId) {
        accountService.deleteAccountById(accountId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Account> getAccount(Integer accountId) {
        return new ResponseEntity<>(accountService.fetchAccountById(accountId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Account>> getAccounts() {
        return new ResponseEntity<>(accountService.fetchAllAccounts(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Account> updateAccount(Integer accountId, Account account) {
      return new ResponseEntity<>(accountService.updateAccountById(accountId, account), HttpStatus.OK);
    }

    @ExceptionHandler(value = AccountDetailsNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> accountDetailsNotFoundExceptionHandler(AccountDetailsNotFoundException ex, HttpServletRequest req) {
        ApiErrorResponse response = new ApiErrorResponse();

        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setPath(req.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

