package org.bank.exeption;

/**
 * This class is created to handle bankException
 */

public class BankException extends Exception {  // extend to the exception class
    public BankException() {
    }
    public BankException(final String message) {
        super(message);
    }

}
