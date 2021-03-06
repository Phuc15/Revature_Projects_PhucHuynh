package org.bank.daos;

import org.bank.exeption.BankException;
import org.bank.model.Customer;

import java.util.List;

public interface CustomerDAO {
    public void registerForNewAccount(Customer customer) throws BankException;

    public boolean validateAccount(String username, String password) throws BankException;

    public List<Customer> displayAllPendingCustomer() throws BankException;

    public boolean approveCustomerAccountById(int id, String username) throws BankException;

    public boolean rejectCustomerAccountById(int id);

    public Customer getCustomerApprover(int id) throws BankException;
}
