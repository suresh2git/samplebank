package com.demo.accounts.service;

import com.demo.accounts.exception.AccountDetailsNotFoundException;
import com.demo.accounts.model.Account;
import com.demo.accounts.repository.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl testClass;

    private static Account account1;

    @BeforeAll
    static void init(){
        account1 = new Account();
        account1.setAccountId(1);
        account1.setAccountHolderName("madhu");
        account1.setAccountName("saving");
        account1.setCountry("IND");
        account1.setCurrency("rupees");
        account1.setIfsc("12345");
        account1.setBalance(new BigDecimal(10000.00));
    }

    @Test
    void testCreateAccount() {
        when(accountRepository.save(any(Account.class))).thenReturn(account1);
        Account actual = testClass.createAccount(account1);

        assertEquals(account1.getAccountHolderName(), actual.getAccountHolderName());

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testUpdateAccountById() {
        Optional<Account> optionalAccount = Optional.ofNullable(AccountServiceImplTest.account1);
        when(accountRepository.findById(eq(1))).thenReturn(optionalAccount);

        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        Account actual = testClass.updateAccountById(1, account1);

        assertEquals(account1.getAccountHolderName(), actual.getAccountHolderName());

        verify(accountRepository, times(1)).findById(anyInt());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testUpdateAccountByIdForAccountDetailsNotFoundException() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(AccountDetailsNotFoundException.class, () -> testClass.updateAccountById(1, account1));

        String expected = "Account not found in db with given account id = 1";
        try{
            testClass.updateAccountById(1, account1);
            fail("Test expecting AccountDetailsNotFoundException");
        } catch (AccountDetailsNotFoundException ex) {
            assertEquals(expected, ex.getMessage());
        }

        verify(accountRepository, times(2)).findById(anyInt());
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void testDeleteAccountById() {
        Optional<Account> optionalAccount = Optional.ofNullable(AccountServiceImplTest.account1);
        when(accountRepository.findById(eq(1))).thenReturn(optionalAccount);

        testClass.deleteAccountById(1);

        verify(accountRepository, times(1)).findById(anyInt());
        verify(accountRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteAccountByIdForAccountDetailsNotFoundException() {
        when(accountRepository.findById(eq(1))).thenReturn(Optional.empty());

        assertThrows(AccountDetailsNotFoundException.class, () -> testClass.deleteAccountById(1));

        String expected = "Account not found in db with given account id = 1";
        try {
            testClass.deleteAccountById(1);
            fail("Test expecting AccountDetailsNotFoundException");
        } catch (AccountDetailsNotFoundException ex) {
            assertEquals(expected, ex.getMessage());
        }

        verify(accountRepository, times(2)).findById(eq(1));
        verify(accountRepository, times(0)).deleteById(anyInt());
    }

    @Test
    void testFetchAccountById() {
        Optional<Account> optionalAccount = Optional.ofNullable(AccountServiceImplTest.account1);
        when(accountRepository.findById(eq(1))).thenReturn(optionalAccount);

        Account actual = testClass.fetchAccountById(1);

        assertEquals(account1.getAccountHolderName(), actual.getAccountHolderName());

        verify(accountRepository, times(1)).findById(eq(1));
    }

    @Test
    void testFetchAccountByIdForAccountDetailsNotFoundException() {
        when(accountRepository.findById(eq(1))).thenReturn(Optional.empty());

        assertThrows(AccountDetailsNotFoundException.class, () -> testClass.fetchAccountById(1));

        String expected = "Account not found in db with given account id = 1";

        try {
            testClass.fetchAccountById(1);
            fail("Test expecting AccountDetailsNotFoundException");
        } catch (AccountDetailsNotFoundException ex) {
            assertEquals(expected, ex.getMessage());
        }

        verify(accountRepository, times(2)).findById(eq(1));
    }

    @Test
    void testFetchAllAccounts() {
        List<Account> accounts = Arrays.asList(account1);

        when(accountRepository.findAll()).thenReturn(accounts);
        List<Account> actual = testClass.fetchAllAccounts();

        assertEquals(accounts.size(), actual.size());
        assertDoesNotThrow(() -> testClass.fetchAllAccounts());
        verify(accountRepository, times(2)).findAll();
    }

    @Test
    void testFetchAllAccountsForAccountDetailsNotFoundException() {
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(AccountDetailsNotFoundException.class, () -> testClass.fetchAllAccounts());

        String expected = "There is no accounts present in db.";
        try {
            testClass.fetchAllAccounts();
            fail("Test expecting AccountDetailsNotFoundException");
        } catch (AccountDetailsNotFoundException ex) {
            assertEquals(expected, ex.getMessage());
        }

        verify(accountRepository, times(2)).findAll();
    }
}