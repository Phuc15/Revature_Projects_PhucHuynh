package org.bank.daos.implementation;

import org.apache.log4j.Logger;
import org.bank.daos.CustomerDAO;
import org.bank.exeption.BankException;
import org.bank.model.Customer;
import org.bank.postgressqlconnection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * * This class is created to to act as DOA and communicate with database
 */
public class CustomerDAOImpl implements CustomerDAO {
    private static Logger logger = Logger.getLogger(CustomerDAOImpl.class);
    /*
     * This method takes customer object as an input and insert it to the database waiting to get approved abd make sure the username is equine
     */
    @Override
    public void registerForNewAccount(Customer customer) throws BankException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select c.username from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                if (customer.getUsername().equals(username)) {
                    throw new BankException("Username already exist. Please try again");
                } else {
                    System.out.println("Your information looks good!!!!");
                }
            }
            String sql1 = "INSERT INTO mybank_schema.customer\n" +
                    "(username, \"password\", customer_name, contact, status)\n" +
                    "VALUES(?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);

            preparedStatement1.setString(1, customer.getUsername());
            preparedStatement1.setString(2, customer.getPassword());
            preparedStatement1.setString(3, customer.getCustomerName());
            preparedStatement1.setString(4, customer.getContact());
            preparedStatement1.setString(5, customer.getStatus());

            int c = preparedStatement1.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println(throwables);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    /*
     * This method takes username and password and compare to the database if there is a match @return true and give the user an access
     * to the customer UI if not it will ask the user to re-enter
     */

    @Override
    public boolean validateAccount(String username, String password) throws BankException {
        boolean status = false;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select c.username, c.password from mybank_schema.customer c where c.username = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String usernameTemp = resultSet.getString("username");
                String passwordTemp = resultSet.getString("password");
                if (username.equals(usernameTemp) && password.equals(passwordTemp)) {
                    status = true;
                } else {
                    throw new BankException("Account not found. Please try again");
                }
            } else {
                throw new BankException("Account is not found. Please try again!");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return status;
    }

    /*
     * this method that will display play all of the customers that are pending to be approved
     */
    @Override
    public List<Customer> displayAllPendingCustomer() throws BankException {
        List<Customer> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT customer_id, customer_name, contact, customer_id, status\n" +
                    "FROM mybank_schema.customer where status = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Pending");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customer_id"));
                customer.setCustomerName(resultSet.getString("customer_name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setStatus(resultSet.getString("status"));
                list.add(customer);
            }
            if (list.isEmpty()) throw new BankException("There is no pending customer.");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }
    /*
     *  this method takes the customer id and approve the status on the database so they can open the bank accounts with
     */
    @Override
    public boolean approveCustomerAccountById(int id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            if(id <0) return false;
            String sql = "UPDATE mybank_schema.customer\n" +
                    "SET status= ? \n" +
                    "WHERE customer_id= ?\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Approved");
            preparedStatement.setInt(2, id);
            int c = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
    /*
     * This method takes customerId and reject the account  by delete it off the database
     */
    @Override
    public boolean rejectCustomerAccountById(int id) {
        try (Connection connection = ConnectionManager.getConnection()) {
            if(id < 0)  return false;
            String sql = "DELETE FROM mybank_schema.customer\n" +
                    "WHERE customer_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int c = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        //should return true when the query is executed successfully
        return true;
    }
}
