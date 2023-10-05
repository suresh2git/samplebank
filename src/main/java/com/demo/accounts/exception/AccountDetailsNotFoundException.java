package com.demo.accounts.exception;

public class AccountDetailsNotFoundException extends RuntimeException {

    public AccountDetailsNotFoundException(String message) {
        super(message);
    }
}
