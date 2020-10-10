package fr.ing.interview.bankaccountkata.exception;

public class InvalidParameters extends Exception{
    public InvalidParameters(String errorMessage) {
        super(errorMessage);
    }
}
