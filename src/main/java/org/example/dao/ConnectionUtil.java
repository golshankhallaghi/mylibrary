package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private ConnectionUtil() {}

    private static Connection connection = init();

    public static Connection getConnection() {
        return connection;
    }

    private static Connection init()  {
        Connection connection = null;
        try {
            String url = "jdbc:h2:file:./MyLibrary/MyLibrary";
            connection = DriverManager.getConnection(url, "sa", "");
            return connection;
        } catch (SQLException ex) {
            System.out.println("Connection Failed! Check output console");
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}