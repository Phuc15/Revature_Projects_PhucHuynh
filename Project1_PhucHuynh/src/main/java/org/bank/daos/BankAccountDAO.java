package org.bank.daos;

import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Transaction;

import java.util.HashMap;
import java.util.List;

/**
 * BankAccountDAO interface
 */

public interface BankAccountDAO {

    public List<BankAccount> viewBankAccount(String username) throws BankException;

    public void deposit(String username, String accountType, double amount) throws BankException;

    public void withdraw(String username, String accountType, double amount) throws BankException;

    public void makeTransfer(String fromUsername, String toUsername, String fromAccountType, double amount) throws BankException;

    public List<Transaction> displayPendingTransaction(String username) throws BankException;

    public boolean acceptPendingTransfer(int pendingTransactionId) throws BankException;
    public List<Transaction> displayPreviousTransactionByUsername(String username) throws BankException;

    public List<BankAccount> displayCustomerBankAccountById(int id) throws BankException;

    public List<Transaction> displayPreviousTransactionById(int customerId) throws BankException;

    public List<Transaction> displayPreviousTransactionByTransactionId(int transactionId) throws BankException;

    public List<Transaction> displayPreviousTransactionByAccountId(int accountId) throws BankException;

    public List<Transaction> displayPreviousTransactionByDate(String date) throws BankException;

    public void applyForBankAccount(String username, String accountType, double amount) throws BankException;

}
