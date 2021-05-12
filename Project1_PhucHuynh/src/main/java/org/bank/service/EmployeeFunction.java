package org.bank.service;


import org.bank.exeption.BankException;

import org.bank.model.BankAccount;
import org.bank.model.Customer;
import org.bank.model.Employee;
import org.bank.model.Transaction;

import java.util.HashMap;
import java.util.List;

/**
 * Employee function class interface
 */

public interface EmployeeFunction {


    public void registerForNewAccount(Customer customer) throws BankException;
    public List<BankAccount> displayCustomerBankAccountById(int id) throws BankException;

    public List<Customer> displayAllPendingCustomer() throws BankException;

    public boolean approveCustomerAccountById(int id, String username) throws BankException;

    public boolean rejectCustomerAccountById(int id) throws BankException;

    public List<Transaction> displayPreviousTransactionById(int customerId) throws BankException;
    public  List<Transaction> displayPreviousTransactionByTransactionId(int transactionId) throws BankException;
    public  List<Transaction> displayPreviousTransactionByAccountId(int accountId) throws  BankException;
    public  List<Transaction> displayPreviousTransactionByDate(String date) throws  BankException;
    public void registerForEmployeeAccount(Employee employee) throws BankException;
    public boolean validateEmployeeAccount(String username, String password) throws BankException;
    public Customer getCustomerApprover(int id);


}
