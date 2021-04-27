package org.bank.postgressqlconnection;

import java.sql.*;

public class ConnectionManager{
    private static String url = "jdbc:postgresql://mydatabase.cqka5g4secjc.us-east-2.rds.amazonaws.com:5432/postgres";
    private static String driverName = "org.postgresql.Driver";
    private static String username = "postgres";
    private static String password = "Phucsonmy1596";
    private static Connection connection;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        //try {
            Class.forName(driverName);
            //System.out.println("Driver loaded");

            connection = DriverManager.getConnection(url, username, password);
            //System.out.println("Connection/Ping Success");
           // Statement statement=connection.createStatement();
            /*String sql="SELECT username, \"password\", firstname, lastname, age, checkingbalance, savingbalance, address, contact, transid\n" +
                    "FROM mybank_schemas.mybank;";
            ResultSet resultSet=statement.executeQuery(sql);

            while(resultSet.next()){
                System.out.print("username : "+resultSet.getString("username"));
                System.out.print(" firstname : "+resultSet.getString("firstname"));
                System.out.print(" age : "+resultSet.getInt("age"));
                System.out.print(" address : "+resultSet.getString("address"));
                System.out.print(" contact : "+resultSet.getLong("contact"));
                System.out.println(" Transit id : "+resultSet.getLong("transid"));*/
            //}


      /*  } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
        return connection;
    }

    //this function is used to close the connection of the function
    /*private void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/

}


