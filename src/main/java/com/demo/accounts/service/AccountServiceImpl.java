package com.demo.accounts.service;

import com.demo.accounts.exception.AccountDetailsNotFoundException;
import com.demo.accounts.model.Account;
import com.demo.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account updateAccountById(Integer accountId, Account account) {
        Optional<Account> accFromRepo = accountRepository.findById(accountId);
        if(!accFromRepo.isPresent()){
            throw new AccountDetailsNotFoundException("Account not found in db with given account id = " + accountId);
        }

        account.setAccountId(accountId);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccountById(Integer accountId) {
        Optional<Account> accFromRepo = accountRepository.findById(accountId);
        if(!accFromRepo.isPresent()){
            throw new AccountDetailsNotFoundException("Account not found in db with given account id = " + accountId);
        }
        accountRepository.deleteById(accountId);
    }

    @Override
    public Account fetchAccountById(Integer accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if(!account.isPresent()){
            throw new AccountDetailsNotFoundException("Account not found in db with given account id = " + accountId);
        }
        return account.get();
    }

    @Override
    public List<Account> fetchAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        if(accounts.isEmpty()) {
            throw new AccountDetailsNotFoundException("There is no accounts present in db.");
        }
        return accounts;
    }
}
