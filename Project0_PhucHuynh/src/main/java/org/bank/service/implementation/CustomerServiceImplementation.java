package org.bank.service.implementation;


import org.bank.daos.BankAccountDAO;
import org.bank.daos.implementation.BankAccountDAOImpl;
import org.bank.daos.CustomerDAO;
import org.bank.daos.implementation.CustomerDAOImpl;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.service.CustomerService;
import java.util.ArrayList;
import java.util.List;


public class CustomerServiceImplementation implements CustomerService {


    BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public boolean validateAccount(String username, String password) throws BankException {

        boolean status = customerDAO.validateAccount(username, password);
        return status;
    }

    @Override
    public void applyForBankAccount(String username, String accountType, double amount) throws BankException {
        bankAccountDAO.applyForBankAccount(username, accountType, amount);
    }

    @Override
    public List<BankAccount> viewBankAccount(String username) throws BankException {
        List<BankAccount> list  = new ArrayList<>();
        list = bankAccountDAO.viewBankAccount(username);
        return list;
    }

    @Override
    public void deposit(String username, String dataType, double amount) throws BankException {
        bankAccountDAO.deposit(username, dataType, amount);
    }

    @Override
    public void withdraw(String username, String dataType, double amount) throws BankException {
        bankAccountDAO.withdraw(username, dataType, amount);
    }

    @Override
    public void makeTransfer(String fromUsername, String toUsername, String fromAccountType, double amount) throws BankException {
        bankAccountDAO.makeTransfer(fromUsername, toUsername, fromAccountType, amount);
    }

    @Override
    public void displayPendingTransaction(String username) throws BankException {
        if(username.isEmpty()){
            throw new BankException("No username is found");
        }else {
            bankAccountDAO.displayPendingTransaction(username);

        }
    }

    @Override
    public boolean acceptPendingTransfer(String username, String accountType) throws BankException {
        boolean status;
            status = bankAccountDAO.acceptPendingTransfer(username, accountType);
        return status;
    }
    //the end
}
