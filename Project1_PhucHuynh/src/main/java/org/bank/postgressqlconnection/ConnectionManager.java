package org.bank.postgressqlconnection;

import java.sql.*;

/**
 * This is a class is used to establish a  connection to the database using url, drivername, optional username and password
 */

public class ConnectionManager {
    private static String url = "jdbc:postgresql://mydatabase.cqka5g4secjc.us-east-2.rds.amazonaws.com:5432/postgres";
    private static String driverName = "org.postgresql.Driver";
    private static String username = "postgres";
    private static String password = "Phucsonmy1596";
    private static Connection connection;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

}


