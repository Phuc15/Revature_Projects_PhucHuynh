package org.bank.daos.implementation;

import org.bank.daos.BankAccountDAO;
import org.bank.exeption.BankException;
import org.bank.model.BankAccount;
import org.bank.model.Customer;
import org.bank.postgressqlconnection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDAOImpl implements BankAccountDAO {
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
            if (list.isEmpty()) {
                throw new BankException("No account is found with this username " + username);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void deposit(String username, String accountType, double amount) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
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
            if (resultSet1.next()) {
                bankAccount.setAccountType(resultSet1.getString("account_type"));
                bankAccount.setBalance(resultSet1.getDouble("balance"));
            }

            if (bankAccount == null) {
                throw new BankException("Account not found.");
            }
            double newAmount = bankAccount.getBalance() + amount;

            String sql2 = "UPDATE mybank_schema.bankaccount\n" +
                    "SET balance = ?, previous_transaction = ? \n" +
                    "WHERE customer_id = ? and account_type = ?;";

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setDouble(1, newAmount);
            preparedStatement2.setDouble(2, amount);
            preparedStatement2.setInt(3, customer.getCustomerId());
            preparedStatement2.setString(4, accountType);
            int c = preparedStatement2.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void withdraw(String username, String accountType, double amount) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
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
            if (resultSet1.next()) {
                bankAccount.setAccountType(resultSet1.getString("account_type"));
                bankAccount.setBalance(resultSet1.getDouble("balance"));
            }

            if (bankAccount == null) {
                throw new BankException("Account not found.");
            }
            if (bankAccount.getBalance() < amount) {
                throw new BankException("Insufficient fund!");
            } else {
                double newAmount = bankAccount.getBalance() - amount;
                String sql2 = "UPDATE mybank_schema.bankaccount\n" +
                        "SET balance = ?, previous_transaction = ? \n" +
                        "WHERE customer_id = ? and account_type = ?;";

                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setDouble(1, newAmount);
                preparedStatement2.setDouble(2, -amount);
                preparedStatement2.setInt(3, customer.getCustomerId());
                preparedStatement2.setString(4, accountType);
                int c = preparedStatement2.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void makeTransfer(String fromUsername, String toUsername, String fromAccountType, double amount) throws BankException {

        Customer customer = new Customer();
        Customer customer1 = new Customer();
        BankAccount bankAccount = new BankAccount();

        List<BankAccount> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {

            String sql = "select c.username, c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fromUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }
            if(customer.getCustomerId() < 0) throw new BankException("There is no customer account with the provided username " + fromUsername);


            String sql1 = "select c.username, c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, toUsername);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                customer1.setCustomerId(resultSet1.getInt("customer_id"));
            }
            String sql5 =  "SELECT account_type\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ?; ";
            PreparedStatement preparedStatement5 = connection.prepareStatement(sql5);
            preparedStatement5.setInt(1, customer1.getCustomerId());
            ResultSet resultSet5 = preparedStatement5.executeQuery();
            while (resultSet5.next()){
                BankAccount bankAccount1 = new BankAccount();
                bankAccount1.setAccountType(resultSet5.getString("account_type"));
                list.add(bankAccount1);
            }
            if(list.isEmpty()) throw new BankException("The other user don't have an account with us! ");

            String sql2 = "SELECT customer_id, account_type, balance\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ?";

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, customer.getCustomerId());
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            if (resultSet2.next()) {
                bankAccount.setAccountType(resultSet2.getString("account_type"));
                bankAccount.setBalance(resultSet2.getFloat("balance"));
            }

            if (bankAccount == null) {
                throw new BankException("Account not found.");
            }
            if (bankAccount.getBalance() < amount) {
                throw new BankException("Insufficient fund!");
            } else {
                double newAmount = bankAccount.getBalance() - amount;
                String sql3 = "UPDATE mybank_schema.bankaccount\n" +
                        "SET balance = ?, previous_transaction = ? \n" +
                        "WHERE customer_id = ? and account_type = ?;";

                PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                preparedStatement3.setDouble(1, newAmount);
                preparedStatement3.setDouble(2, -amount);
                preparedStatement3.setInt(3, customer.getCustomerId());
                preparedStatement3.setString(4, fromAccountType);
                int c = preparedStatement3.executeUpdate();

            }


            String sql4 ="UPDATE mybank_schema.bankaccount\n" +
                    "SET pending_transaction= ? \n" +
                    "WHERE customer_id= ?\n";;
            PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
            preparedStatement4.setDouble(1, amount);
            preparedStatement4.setInt(2, customer1.getCustomerId());
            int c1 = preparedStatement4.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void displayPendingTransaction(String username) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
        try(Connection connection = ConnectionManager.getConnection()) {

            String sql = "select c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }
            if (customer.getCustomerId() < 0)
                throw new BankException("There is no customer id with the provided username " + username);

            String sql1 = "SELECT pending_transaction\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ?";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, customer.getCustomerId());
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            while (resultSet1.next()) {
                bankAccount.setPendingTransaction(resultSet1.getDouble("pending_transaction"));
            }
            if (bankAccount.getPendingTransaction() == 0) {
                throw new BankException("There is no pending transaction");
            } else {
                System.out.println("We have a amount $" + bankAccount.getPendingTransaction() + " waiting to be accepted");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean acceptPendingTransfer(String username, String accountType) throws BankException {
        Customer customer = new Customer();
        BankAccount bankAccount = new BankAccount();
        try(Connection connection = ConnectionManager.getConnection()){
            String sql = "select c.customer_id from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customer_id"));
            }

            if(customer.getCustomerId() < 0) throw new BankException("There is no customer id with the provided username " +username);

            String sql1 = "SELECT pending_transaction\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ?";

            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, customer.getCustomerId());
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            while(resultSet1.next()){
                bankAccount.setPendingTransaction(resultSet1.getDouble("pending_transaction"));
            }
            if(bankAccount.getPendingTransaction() <=0 ) {
                throw new BankException("invalid pending transaction amount");
            }else {
                deposit(username, accountType, bankAccount.getPendingTransaction());
            }
            String sql2 ="UPDATE mybank_schema.bankaccount\n" +
                    "SET pending_transaction= ? \n" +
                    "WHERE customer_id= ?\n";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setDouble(1, 0);
            preparedStatement2.setInt(2, customer.getCustomerId());
            int c = preparedStatement2.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List <BankAccount> displayCustomerBankAccountById(int id) throws BankException {
        List<BankAccount> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT account_type, previous_transaction, balance\n" +
                    "FROM mybank_schema.bankaccount WHERE customer_id = ?;\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                BankAccount bankAccount = new BankAccount();
                bankAccount.setAccountType(resultSet.getString("account_type"));
                bankAccount.setBalance(resultSet.getDouble("balance"));
                bankAccount.setPreviousTransaction(resultSet.getDouble("previous_transaction"));
                list.add(bankAccount);
            }

            if(list.isEmpty()){
                throw new BankException("No bank account with customer id " + id);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list ;
    }


    @Override
    public List<BankAccount> displayPreviousTransactionById(int customerId) throws BankException {
        List<BankAccount> list = new ArrayList<>();
        try(Connection connection = ConnectionManager.getConnection();) {
            String sql = "SELECT account_type, previous_transaction\n" +
                    "FROM mybank_schema.bankaccount where customer_id = ? ;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                BankAccount bankAccount = new BankAccount();
                bankAccount.setAccountType(resultSet.getString("account_type"));
                bankAccount.setPreviousTransaction(resultSet.getDouble("previous_transaction"));
                list.add(bankAccount);
            }
            if(list.isEmpty()) throw new BankException("No previous transaction found for this customer id " + customerId);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

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
            if(customer.getStatus().equals("Pending")){
                throw new BankException("Your account is still pending to be approved. Thank you!");
            }else {

                String sql2 = "SELECT account_type\n" +
                        "FROM mybank_schema.bankaccount where customer_id = ?; ";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, customer.getCustomerId());
                ResultSet resultSet1 = preparedStatement2.executeQuery();

                while (resultSet1.next()) {
                    BankAccount bankAccount = new BankAccount();
                    bankAccount.setAccountType(resultSet1.getString("account_type"));
                   /*); =  if (accountType.equals(bankType)) {
                        throw new BankException("You already have a " + accountType + " account");
                    } else {
                        String sql1 = "INSERT INTO mybank_schema.bankaccount\n" +
                                "(customer_id, account_type, balance)\n" +
                                "VALUES(?, ?, ?);\n";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                        preparedStatement1.setInt(1, customer.getCustomerId());
                        preparedStatement1.setString(2, accountType);
                        preparedStatement1.setDouble(3, amount);
                        int c = preparedStatement1.executeUpdate();
                    }*/
                    list.add(bankAccount);

                }
                for(BankAccount account: list){
                    String accountTyp = account.getAccountType();
                    if(accountTyp.equals(accountType)){
                        throw new BankException("You already have a " + accountType + " account");
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
            System.out.println(throwables);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
