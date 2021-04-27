package org.bank.service;


import org.bank.exeption.BankException;

import org.bank.model.BankAccount;
import org.bank.model.Customer;

import java.util.List;

public interface EmployeeFunction {


    public void registerForNewAccount(Customer customer) throws BankException;

    public List<BankAccount> displayCustomerBankAccountById(int id) throws BankException;

    public List<Customer> displayAllPendingCustomer() throws BankException;

    public boolean approveCustomerAccountById(int id) throws BankException;

    public boolean rejectCustomerAccountById(int id) throws BankException;

    public List<BankAccount> displayPreviousTransactionById(int customerId) throws BankException;


}
