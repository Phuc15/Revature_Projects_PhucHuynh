package org.bank.service.implementation;
import org.bank.daos.BankAccountDAO;
import org.bank.daos.implementation.BankAccountDAOImpl;
import org.bank.daos.CustomerDAO;
import org.bank.daos.implementation.CustomerDAOImpl;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Transaction;
import org.bank.service.CustomerService;
import java.util.ArrayList;
import java.util.List;


public class CustomerServiceImplementation implements CustomerService {


    BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();

    public CustomerServiceImplementation() {
        bankAccountDAO = new BankAccountDAOImpl();
        customerDAO = new CustomerDAOImpl();
    }

    public CustomerServiceImplementation(BankAccountDAO bankAccountDAO, CustomerDAO customerDAO) {
        this.bankAccountDAO = bankAccountDAO;
        this.customerDAO = customerDAO;
    }

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
        List<BankAccount> list = new ArrayList<>();
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
    public List<Transaction> displayPendingTransaction(String username) throws BankException {
        List<Transaction> list = new ArrayList<>();
        if (username.isEmpty()) {
            throw new BankException("No username is found");
        } else {
         list =  bankAccountDAO.displayPendingTransaction(username);

        }
        return list;
    }

    @Override
    public boolean acceptPendingTransfer(int pendingTransactionId) throws BankException {
        boolean status;
        status = bankAccountDAO.acceptPendingTransfer(pendingTransactionId);
        return status;
    }

    @Override
    public List<Transaction> displayPreviousTransactionByUsername(String username) throws BankException {
        List<Transaction> list = new ArrayList<>();
        list = bankAccountDAO.displayPreviousTransactionByUsername(username);
        return list;
    }
    //the end
}
