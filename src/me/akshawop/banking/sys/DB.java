package me.akshawop.banking.sys;

import java.sql.*;

class DB {
    static Connection connect() {
        String url = "jdbc:mysql://localhost:3306/banking_system";
        String uname = "root";
        String pass = "1507";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, uname, pass);
        } catch (ClassNotFoundException e) {
            System.err.println("Fatal Error: JDBC DRIVER NOT FOUND!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Fatal Error: Can't connect to the Database!");
            System.err.println("Check if the Database is up and running properly on 'localhost:3306'");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Fatal Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        System.exit(1);
        return null;
    }
}
