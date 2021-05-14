package org.bank.daos.implementation;

import org.apache.log4j.Logger;
import org.bank.daos.EmployeeDAO;
import org.bank.exeption.BankException;
import org.bank.model.Employee;
import org.bank.postgressqlconnection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAOImpl implements EmployeeDAO {
    private static Logger logger = Logger.getLogger(EmployeeDAOImpl.class);
    @Override
    public void registerForEmployeeAccount(Employee employee) throws BankException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select e.username_employee from mybank_schema.employee e where e.username_employee = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getEmployeeUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username_employee");
                if (employee.getEmployeeUsername().equals(username)) {
                    throw new BankException("Username already exist. Please try again");
                } else {
                    System.out.println("Your information looks good!!!!");
                }
            }
            String sql1 = "INSERT INTO mybank_schema.employee\n" +
                    "(username_employee, \"password_employee\", employee_name)\n" +
                    "VALUES(?, ?, ?);";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);

            preparedStatement1.setString(1, employee.getEmployeeUsername());
            preparedStatement1.setString(2, employee.getEmployeePassword());
            preparedStatement1.setString(3, employee.getEmployeeName());

            int c = preparedStatement1.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }

    @Override
    public boolean validateEmployeeAccount(String username, String password) throws BankException {
        boolean status = false;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select e.username_employee, e.password_employee from mybank_schema.employee e where e.username_employee = ?;\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String usernameTemp = resultSet.getString("username_employee");
                String passwordTemp = resultSet.getString("password_employee");
                if (username.equals(usernameTemp) && password.equals(passwordTemp)) {
                    status = true;
                } else {
                    status = false;
                    throw new BankException("Account not found. Please try again");
                }
            } else {
                throw new BankException("Account is not found. Please try again!");
            }

        } catch (SQLException throwables) {
            logger.error(throwables);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        return status;

    }
}
