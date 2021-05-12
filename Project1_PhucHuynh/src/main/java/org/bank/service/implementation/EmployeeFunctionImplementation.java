package org.bank.service.implementation;


import org.bank.daos.BankAccountDAO;
import org.bank.daos.EmployeeDAO;
import org.bank.daos.implementation.BankAccountDAOImpl;
import org.bank.daos.CustomerDAO;
import org.bank.daos.implementation.CustomerDAOImpl;
import org.bank.daos.implementation.EmployeeDAOImpl;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Customer;
import org.bank.model.Employee;
import org.bank.model.Transaction;
import org.bank.service.EmployeeFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EmployeeFunctionImplementation implements EmployeeFunction {


    BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Override
    public void registerForNewAccount(Customer customer) throws BankException {
        customerDAO.registerForNewAccount(customer);
    }

    @Override
    public List<BankAccount> displayCustomerBankAccountById(int id) throws BankException {
        List<BankAccount> list = new ArrayList<>();
        if (id <= 0) {
            throw new BankException("There is no customer with id: " + id);
        } else {
            list = bankAccountDAO.displayCustomerBankAccountById(id);
        }
        return list;
    }

    @Override
    public List<Customer> displayAllPendingCustomer() throws BankException {
        List<Customer> list = new ArrayList<>();
        list = customerDAO.displayAllPendingCustomer();
        return list;
    }

    @Override
    public boolean approveCustomerAccountById(int id, String username) throws BankException {
        boolean state = false;
        if (id <= 0) {
            throw new BankException("There is no customer with the id " + id);
        } else {
            state = customerDAO.approveCustomerAccountById(id, username);
        }
        return state;
    }

    @Override
    public boolean rejectCustomerAccountById(int id) throws BankException {
        boolean state = false;
        if (id <= 0) {
            throw new BankException("There is no customer with the id " + id);
        } else {
            state = customerDAO.rejectCustomerAccountById(id);
        }
        return state;
    }

    @Override
    public List<Transaction> displayPreviousTransactionById(int customerId) throws BankException {
        List<Transaction> list = new ArrayList<>();
        list = bankAccountDAO.displayPreviousTransactionById(customerId);
        return list;
    }

    @Override
    public List<Transaction> displayPreviousTransactionByTransactionId(int transactionId) throws BankException {
        List<Transaction> list = new ArrayList<>();
        list = bankAccountDAO.displayPreviousTransactionByTransactionId(transactionId);
        return list;
    }

    @Override
    public List<Transaction> displayPreviousTransactionByAccountId(int accountId) throws BankException {
        List<Transaction> list = new ArrayList<>();
        list = bankAccountDAO.displayPreviousTransactionByAccountId(accountId);
        return list;
    }

    @Override
    public List<Transaction> displayPreviousTransactionByDate(String date) throws BankException {
        List<Transaction> list = new ArrayList<>();
        list = bankAccountDAO.displayPreviousTransactionByDate(date);
        return list;
    }

    @Override
    public void registerForEmployeeAccount(Employee employee) throws BankException {
     employeeDAO.registerForEmployeeAccount(employee);
    }

    @Override
    public boolean validateEmployeeAccount(String username, String password) throws BankException {
        return employeeDAO.validateEmployeeAccount(username, password);
    }

    @Override
    public Customer getCustomerApprover(int id) {
        return customerDAO.getCustomerApprover(id);
    }


}
