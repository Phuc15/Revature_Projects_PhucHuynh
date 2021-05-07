package org.bank.daos;

import org.bank.exeption.BankException;
import org.bank.model.BankAccount;

import java.util.List;

/**
 * BankAccountDAO interface
 */

public interface BankAccountDAO {

    public List<BankAccount> viewBankAccount(String username) throws BankException;

    public void deposit(String username, String accountType, double amount) throws BankException;

    public void withdraw(String username, String accountType, double amount) throws BankException;

    public void makeTransfer(String fromUsername, String toUsername, String fromAccountType, double amount) throws BankException;

    public void displayPendingTransaction(String username) throws BankException;

    public boolean acceptPendingTransfer(int pendingTransactionId) throws BankException;

    public List<BankAccount> displayCustomerBankAccountById(int id) throws BankException;

    public void displayPreviousTransactionById(int customerId) throws BankException;

    public void applyForBankAccount(String username, String accountType, double amount) throws BankException;

}
