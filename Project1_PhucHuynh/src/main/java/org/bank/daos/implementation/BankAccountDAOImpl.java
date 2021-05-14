package org.bank.daos.implementation;

import org.apache.log4j.Logger;
import org.bank.daos.BankAccountDAO;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Customer;
import org.bank.model.Transaction;
import org.bank.postgressqlconnection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is created to to act as DOA and communicate with database
 */


public class BankAccountDAOImpl implements BankAccountDAO {
    private static Logger logger = Logger.getLogger(BankAccountDAOImpl.class);
    /*
     * this method takes username adn retrieve bank account information from the database
     */
    @Override
    public List<BankAccount> viewBankAccount(String username) throws BankException {
        List<BankAccount> list = new ArrayList<>();
        Customer customer = new Customer();
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select c.username, c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }

            String sql1 = "SELECT customer_id, account_type, balance\n" +
                    "from mybank_schema.bankaccount where customer_id = ?;";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, customer.getCustomerId());
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {

                BankAccount bankAccount = new BankAccount();
                bankAccount.setAccountType(resultSet1.getString("account_type"));
                bankAccount.setBalance(resultSet1.getDouble("balance"));
                list.add(bankAccount);
            }
            if (list.isEmpty()) throw new BankException("No account is found with this username");

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
           logger.error(e);
        }

        return list;
    }

    /*
     * this method takes customer username, accountType and amount and add it into the database and modify the previous
     * transaction
     */

    @Override
    public void deposit(String username, String accountType, double amount) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
        BankAccount bankAccount1 = new BankAccount();
        List<String> list = new ArrayList<>();
        logger.info(accountType);
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select c.username, c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }

            String sql1 = "SELECT customer_id, account_type, balance\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ?";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, customer.getCustomerId());
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while(resultSet1.next()) {
                bankAccount.setAccountType(resultSet1.getString("account_type"));
                bankAccount.setBalance(resultSet1.getDouble("balance"));
                list.add(bankAccount.getAccountType());
            }
            for(int i = 0; i<list.size(); i++) {
                if (list.get(i).equals(accountType)) {
                    double newAmount = bankAccount.getBalance() + amount;
                    String sql3 = "select b.account_id from mybank_schema.bankaccount b where b.customer_id = ? and b.account_type = ?;\n";
                    PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                    preparedStatement3.setInt(1, customer.getCustomerId());
                    preparedStatement3.setString(2, accountType);
                    ResultSet resultSet3 = preparedStatement3.executeQuery();
                    while (resultSet3.next()) {
                        bankAccount1.setAccountId(resultSet3.getInt("account_id"));
                    }

                    String sql4 = "INSERT INTO mybank_schema.\"transaction\"\n" +
                            "(account_id, previous_transaction)\n" +
                            "VALUES(?, ?);";

                    PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
                    preparedStatement4.setInt(1, bankAccount1.getAccountId());
                    preparedStatement4.setDouble(2, amount);

                    int c = preparedStatement4.executeUpdate();

                    //------------------------------------
                    String sql2 = "UPDATE mybank_schema.bankaccount\n" +
                            "SET balance = ?\n" +
                            "WHERE customer_id = ? and account_type = ?;";


                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                    preparedStatement2.setDouble(1, newAmount);
                    preparedStatement2.setInt(2, customer.getCustomerId());
                    preparedStatement2.setString(3, accountType);
                    int c1 = preparedStatement2.executeUpdate();
                }
            }

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }


    }

    /*
     * this method takes customer username, accountType and amount and retrieve that data and manipulate the data here by subtract the current amount by the requested amount
     *  then update the database the database and modify the previous transaction
     */
    @Override
    public void withdraw(String username, String accountType, double amount) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
        BankAccount bankAccount1 = new BankAccount();
        List<String> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select c.username, c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }

            String sql1 = "SELECT customer_id, account_type, balance\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ?";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, customer.getCustomerId());
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                bankAccount.setAccountType(resultSet1.getString("account_type"));
                bankAccount.setBalance(resultSet1.getDouble("balance"));
                list.add(bankAccount.getAccountType());
            }

            if (bankAccount.getBalance() < amount) {
                throw new BankException("Insufficient fund!");
            } else {

                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).equals(accountType)) {
                        double newAmount = bankAccount.getBalance() - amount;
                        String sql3 = "select b.account_id from mybank_schema.bankaccount b where b.customer_id = ? and b.account_type = ?;\n";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                        preparedStatement3.setInt(1, customer.getCustomerId());
                        preparedStatement3.setString(2, accountType);
                        ResultSet resultSet3 = preparedStatement3.executeQuery();
                        while (resultSet3.next()) {
                            bankAccount1.setAccountId(resultSet3.getInt("account_id"));
                        }

                        String sql4 = "INSERT INTO mybank_schema.\"transaction\"\n" +
                                "(account_id, previous_transaction)\n" +
                                "VALUES(?, ?);";

                        PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
                        preparedStatement4.setInt(1, bankAccount1.getAccountId());
                        preparedStatement4.setDouble(2, -amount);

                        int c1 = preparedStatement4.executeUpdate();
                        //update teh balance
                        String sql2 = "UPDATE mybank_schema.bankaccount\n" +
                                "SET balance = ? \n" +
                                "WHERE customer_id = ? and account_type = ?;";

                        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                        preparedStatement2.setDouble(1, newAmount);

                        preparedStatement2.setInt(2, customer.getCustomerId());
                        preparedStatement2.setString(3, accountType);
                        int c = preparedStatement2.executeUpdate();
                    }
                }
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
           logger.error(e);
        }

    }

    /*
     * This method takes usernames from both sender and receiver, account type you want to send money from and the amount
     * after that it will retrieve the data and calculate  locally  then make a update to the database
     *  Assumption: the transfer will go to the pending transaction queue and waiting for the receiver to accept it
     */
    @Override
    public void makeTransfer(String fromUsername, String toUsername, String fromAccountType, double amount) throws BankException {

        Customer customer = new Customer();
        Customer customer1 = new Customer();
        BankAccount bankAccount = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        List<BankAccount> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "select c.username, c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fromUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }
            if (customer.getCustomerId() < 0)
                throw new BankException("There is no customer account with the provided username " + fromUsername);


            String sql1 = "select c.username, c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, toUsername);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                customer1.setCustomerId(resultSet1.getInt("customer_id"));
            }
            //checking if other user have a account w us
            String sql5 = "SELECT account_id\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ? and account_type =?; ";
            PreparedStatement preparedStatement5 = connection.prepareStatement(sql5);
            preparedStatement5.setInt(1, customer1.getCustomerId());
            preparedStatement5.setString(2, "Checking");
            ResultSet resultSet5 = preparedStatement5.executeQuery();

            while (resultSet5.next()) {

                bankAccount2.setAccountId(resultSet5.getInt("account_id"));
                list.add(bankAccount2);
            }
            if (list.isEmpty()) throw new BankException("The other user don't have an account with us! ");


            String sql2 = "SELECT customer_id, balance, account_id\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ? and account_type =?;";

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, customer.getCustomerId());
            preparedStatement2.setString(2, fromAccountType);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            if (resultSet2.next()) {
                bankAccount.setBalance(resultSet2.getDouble("balance"));
                bankAccount.setAccountId(resultSet2.getInt("account_id"));
            }

            if (bankAccount.getBalance() < 0 || bankAccount.getAccountId() <0)  throw new BankException("Account not found.");

            if (bankAccount.getBalance() < amount) {
                throw new BankException("Insufficient fund!");
            } else {

                double newAmount = bankAccount.getBalance() - amount;
                String sql3 = "UPDATE mybank_schema.bankaccount\n" +
                        "SET balance = ? \n" +
                        "WHERE customer_id = ? and account_type = ?;";

                PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                preparedStatement3.setDouble(1, newAmount);
                preparedStatement3.setInt(2, customer.getCustomerId());
                preparedStatement3.setString(3, fromAccountType);
                int c = preparedStatement3.executeUpdate();

            }

            String sql4 = "INSERT INTO mybank_schema.\"transaction\"\n" +
                    "(account_id, previous_transaction)\n" +
                    "VALUES(?, ?);";
            PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
            preparedStatement4.setInt(1, bankAccount.getAccountId());
            preparedStatement4.setDouble(2, -amount);
            int c1 = preparedStatement4.executeUpdate();


            String sql6 = "INSERT INTO mybank_schema.\"transaction\"\n" +
                    "(account_id, pending_transaction)\n" +
                    "VALUES(?, ?);";
            PreparedStatement preparedStatement6 = connection.prepareStatement(sql6);
            preparedStatement6.setInt(1, bankAccount2.getAccountId());
            preparedStatement6.setDouble(2, amount);
            int c2 = preparedStatement6.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
           logger.error(e);
        }

    }

    /*
     * This method takes the username and checking to see if there are any pending transactions waiting to accepted
     * If yes, then it will display to the console with  the amount and transaction id
     */

    @Override
    public List<Transaction> displayPendingTransaction(String username) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
        Transaction transaction = new Transaction();
        List<Transaction> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "select c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }
            String sql3 = "select b.account_id from mybank_schema.bankaccount b where b.customer_id = ? and b.account_type =?;";
            PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
            preparedStatement3.setInt(1, customer.getCustomerId());
            preparedStatement3.setString(2, "Checking");
            ResultSet resultSet3 = preparedStatement3.executeQuery();
            while (resultSet3.next()) {
                bankAccount.setAccountId(resultSet3.getInt("account_id"));
            }
            if(bankAccount.getAccountId()< 0) throw new BankException("No saving account found with this customer id" + customer.getCustomerId());

            if (customer.getCustomerId() < 0)
                throw new BankException("There is no customer id with the provided username " + username);

            String sql1 = "SELECT account_id, transaction_id , pending_transaction, date\n" +
                    "FROM mybank_schema.transaction where account_id = ? and pending_transaction > 0; ";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, bankAccount.getAccountId());
            ResultSet resultSet1 = preparedStatement1.executeQuery();


            while (resultSet1.next()) {
                transaction.setPendingTransaction(resultSet1.getDouble("pending_transaction"));
                transaction.setTransactionId(resultSet1.getInt("transaction_id"));
                transaction.setDate(resultSet1.getString("date"));
                if(transaction.getPendingTransaction() < 0  || transaction.getTransactionId() <0) throw new BankException("No transaction found with the account id");
                list.add(transaction);
            }
            if(list.isEmpty()) throw new BankException("There is no pending transaction");
        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
           logger.error(e);
        }
       return list;
    }
    /*
     * This method allow the customer to input the transaction id and transfer the amount to the the checking account
     */

    @Override
    public boolean acceptPendingTransfer(int pendingTransactionId) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
        BankAccount bankAccount1 = new BankAccount();
        Transaction transaction = new Transaction();

        try (Connection connection = ConnectionManager.getConnection()) {
            if(pendingTransactionId<0) return false;
            String spl = "SELECT account_id, pending_transaction\n" +
                    "FROM mybank_schema.\"transaction\" where transaction_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(spl);
            preparedStatement.setInt(1, pendingTransactionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bankAccount.setAccountId(resultSet.getInt("account_id"));
                transaction.setPendingTransaction(resultSet.getDouble("pending_transaction"));
            }
            if(transaction.getPendingTransaction() <=0) return false;


            String sql1 = "SELECT balance FROM mybank_schema.bankaccount WHERE account_id = ?; ";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, bankAccount.getAccountId());
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                bankAccount.setBalance(resultSet1.getDouble("balance"));
            }
            double newAmount = bankAccount.getBalance() + transaction.getPendingTransaction();

            String sql2 = "UPDATE mybank_schema.bankaccount\n" +
                    "SET balance = ? \n" +
                    "WHERE account_id = ?";

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setDouble(1, newAmount);
            preparedStatement2.setInt(2, bankAccount.getAccountId());

            String sql4 = "INSERT INTO mybank_schema.\"transaction\"\n" +
                    "(account_id, previous_transaction)\n" +
                    "VALUES(?, ?);";
            PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
            preparedStatement4.setInt(1, bankAccount.getAccountId());
            preparedStatement4.setDouble(2, transaction.getPendingTransaction());
            int c1 = preparedStatement4.executeUpdate();

            String sql5 = "DELETE FROM mybank_schema.\"transaction\"\n" +
                    "WHERE transaction_id= ?;";

            PreparedStatement preparedStatement3 = connection.prepareStatement(sql5);
            preparedStatement3.setInt(1, pendingTransactionId);

            int c2 = preparedStatement3.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return true;
    }

    @Override
    public List<Transaction> displayPreviousTransactionByUsername(String username) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
        List<Transaction> list = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "select c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }

            list = displayPreviousTransactionById(customer.getCustomerId());
            if(list.isEmpty()) throw new BankException("There is no transactions in the record");
        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return list;
    }
    /*
     * This method takes customer id and return the account id
     */

    @Override
    public List<BankAccount> displayCustomerBankAccountById(int id) throws BankException {
        List<BankAccount> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT account_type, balance\n" +
                    "FROM mybank_schema.bankaccount WHERE customer_id = ?;\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setAccountType(resultSet.getString("account_type"));
                bankAccount.setBalance(resultSet.getDouble("balance"));
                list.add(bankAccount);

            }
            if (list.isEmpty()) {throw new BankException("No bank account with customer id");}

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return list;
    }

    /*
     * This method takes customer id and display all the transaction with account id that customer have
     */
    @Override
    public List<Transaction> displayPreviousTransactionById(int customerId) throws BankException {
        List<Transaction> list = new ArrayList<>();
        List<BankAccount> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        BankAccount bankAccount = new BankAccount();
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT account_id\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ? ;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bankAccount.setAccountId(resultSet.getInt("account_id"));
                int a = bankAccount.getAccountId();
                list1.add(bankAccount);
                list2.add(a);
            }

            //System.out.println(list1);
            for (Integer i: list2) {
                String sql1 = "SELECT previous_transaction, date, account_id \n" +
                        "FROM mybank_schema.\"transaction\" WHERE account_id =? and previous_transaction != 0;";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1, i);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setPreviousTransaction(resultSet1.getDouble("previous_transaction"));
                    transaction.setDate(resultSet1.getString("date"));
                    transaction.setAccountId(resultSet1.getInt("account_id"));
                    list.add(transaction);
                }
            }

            if (list.isEmpty()) throw new BankException("No account found  found for this customer id");
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Transaction> displayPreviousTransactionByTransactionId(int transactionId) throws BankException {
        List<Transaction> list = new ArrayList<>();
        try(Connection connection = ConnectionManager.getConnection()){

            String sql1 = "SELECT previous_transaction, date, account_id \n" +
                    "FROM mybank_schema.\"transaction\" WHERE transaction_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1, transactionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setPreviousTransaction(resultSet.getDouble("previous_transaction"));
                transaction.setDate(resultSet.getString("date"));
                transaction.setAccountId(resultSet.getInt("account_id"));
                list.add(transaction);
            }
            if(list.isEmpty()) throw new BankException("Transactions not found with this transaction id");
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Transaction> displayPreviousTransactionByAccountId(int accountId) throws BankException {

        List<Transaction> list = new ArrayList<>();
        try(Connection connection = ConnectionManager.getConnection()){

            String sql = "SELECT previous_transaction, date, account_id \n" +
                    "FROM mybank_schema.\"transaction\" WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setPreviousTransaction(resultSet.getDouble("previous_transaction"));
                transaction.setDate(resultSet.getString("date"));
                transaction.setAccountId(resultSet.getInt("account_id"));
                list.add(transaction);
            }
            if(list.isEmpty()) throw new BankException("Transactions not found with this account id");
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Transaction> displayPreviousTransactionByDate(String date) throws BankException {

        List<Transaction> list = new ArrayList<>();
        try(Connection connection = ConnectionManager.getConnection()){

            String sql = "select * from mybank_schema.\"transaction\" t where \"date\"::text like ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setPreviousTransaction(resultSet.getDouble("previous_transaction"));
                transaction.setAccountId(resultSet.getInt("account_id"));
                transaction.setDate(resultSet.getString("date"));
                list.add(transaction);
            }
            if(list.isEmpty()) throw new BankException("Transactions not found with this date");

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return list;
    }

    /*
     * This method takes username, account type and amount, open an account for the customer after evaluating to see if the customer is approved to open an bank account
     *
     */

    @Override
    public void applyForBankAccount(String username, String accountType, double amount) throws BankException {

        Customer customer = new Customer();
        List<BankAccount> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select c.username, c.customer_id, c.status from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setStatus(resultSet.getString("status"));
            }
            if (customer.getStatus().equals("Pending")) {
                throw new BankException("Your account is still pending to be approved. Thank you!");
            } else {

                String sql2 = "SELECT account_type\n" +
                        "FROM mybank_schema.bankaccount where customer_id = ?; ";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, customer.getCustomerId());
                ResultSet resultSet1 = preparedStatement2.executeQuery();

                while (resultSet1.next()) {
                    BankAccount bankAccount = new BankAccount();
                    bankAccount.setAccountType(resultSet1.getString("account_type"));
                    list.add(bankAccount);

                }
                for (BankAccount account : list) {
                    String accountTyp = account.getAccountType();
                    if (accountTyp.equals(accountType)) {
                        throw new BankException("You already have this account");
                    }
                }

                String sql1 = "INSERT INTO mybank_schema.bankaccount\n" +
                        "(customer_id, account_type, balance)\n" +
                        "VALUES(?, ?, ?);\n";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setInt(1, customer.getCustomerId());
                preparedStatement1.setString(2, accountType);
                preparedStatement1.setDouble(3, amount);
                int c = preparedStatement1.executeUpdate();
            }
        } catch (SQLException throwables) {
          logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }
}
