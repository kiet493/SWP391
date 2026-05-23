package com.fruitshop.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * File: DBConnection.java
 * Description: Database connection utility class for SQL Server.
 */
public class DBConnection {

    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            props.load(input);
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error loading database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
