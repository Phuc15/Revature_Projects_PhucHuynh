package org.bank.service.implementation;


import org.bank.daos.BankAccountDAO;
import org.bank.daos.implementation.BankAccountDAOImpl;
import org.bank.daos.CustomerDAO;
import org.bank.daos.implementation.CustomerDAOImpl;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Customer;
import org.bank.service.EmployeeFunction;
import java.util.ArrayList;
import java.util.List;


public class EmployeeFunctionImplementation implements EmployeeFunction {


BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();
CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public void  registerForNewAccount(Customer customer) throws BankException {
          customerDAO.registerForNewAccount(customer);
    }

    @Override
    public List<BankAccount> displayCustomerBankAccountById(int id) throws BankException {
        List<BankAccount> list = new ArrayList<>();
        if(id<=0){
            throw new BankException("There is no customer with id: "+id);
        }else {
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
    public boolean approveCustomerAccountById(int id) throws BankException {
        boolean state = false;
        if(id <= 0){
            throw new BankException("There is no customer with the id "+id);
        }else{
            state = customerDAO.approveCustomerAccountById(id);
        }
        return state;
    }

    @Override
    public boolean rejectCustomerAccountById(int id) throws BankException {
        boolean state = false;
        if(id <= 0){
            throw new BankException("There is no customer with the id "+id);
        }else{
            state = customerDAO.rejectCustomerAccountById(id);
        }
        return state;
    }

    @Override
    public List<BankAccount> displayPreviousTransactionById(int customerId) throws BankException {
        List<BankAccount> list = new ArrayList<>();
        if(customerId <= 0){
            throw new BankException("Customer ID is invalid");
        }else {
            list = bankAccountDAO.displayPreviousTransactionById(customerId);
        }
        return list;
    }



}
