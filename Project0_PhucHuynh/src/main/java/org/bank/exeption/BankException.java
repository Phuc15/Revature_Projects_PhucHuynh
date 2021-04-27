package org.bank.exeption;

//throw exception
public class BankException extends Exception {  // extend to the exception class
    public BankException() {
    }
    public BankException(final String message) {
        super(message);
    }

}
