package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String url = "jdbc:mysql://localhost:3306/LibraryDB";
    private static final String username = "root";
    private static final String password = null;

    @SuppressWarnings("exports")
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
