package fr.ing.interview.bankaccountkata.exception;

public class AccountNotFound extends Exception{

    public AccountNotFound(String errorMessage) {
        super(errorMessage);
    }
}
