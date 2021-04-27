package org.bank.service;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;

import java.util.List;

public interface CustomerService {

    public boolean validateAccount(String username, String password) throws BankException;

    public void  applyForBankAccount(String username, String accountType, double amount) throws BankException;

    public List<BankAccount> viewBankAccount(String username) throws BankException;

    public void deposit(String username, String dataType, double amount) throws BankException;
    public void withdraw(String username, String dataType, double amount) throws BankException;
    public void makeTransfer(String fromUsername, String toUsername, String fromAccountType,  double amount) throws BankException;
    public void displayPendingTransaction(String username) throws BankException;
    public boolean acceptPendingTransfer(String username, String accountType) throws BankException;
}
