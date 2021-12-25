package com.example.anticafe.exception;

public final class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException() {super("Account not found");}
}
