package com.example.helllllllooo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/masathai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Load the MySQL JDBC Driver during class initialization
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Handle the exception by logging and rethrowing a runtime exception
            e.printStackTrace();
            throw new RuntimeException("Error loading MySQL JDBC Driver", e);
        }
    }

    // Establish a connection to the database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // Handle the exception by logging and rethrowing a runtime exception
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static DbConnection getDatabaseConnection() {
        return null;
    }
}