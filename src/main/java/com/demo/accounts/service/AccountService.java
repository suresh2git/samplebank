package com.demo.accounts.service;

import com.demo.accounts.model.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(Account account);

    Account updateAccountById(Integer accountId, Account account);

    void deleteAccountById(Integer accountId);

    Account fetchAccountById(Integer accountId);

    List<Account> fetchAllAccounts();

}
