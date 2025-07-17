package org.example.dao;

import org.example.exception.MyConnectionException;
import org.example.exception.MySQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private ConnectionUtil() {
    }

    private static final Connection connection;

    static {
        try {
            connection = init();
        } catch (MySQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection()  {
            return connection;
    }

    private static Connection init() throws MySQLException {
        Connection connection = null;
        try {
            String url = "jdbc:h2:file:./MyLibrary/MyLibrary";
            connection = DriverManager.getConnection(url, "sa", "");
            return connection;
        } catch (SQLException e) {
            throw new MySQLException("can not reach SQL" + e.getMessage());
        }
    }

    public static void closeConnection() throws MySQLException {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("connection closed");
            } catch (SQLException e) {
                throw new MySQLException("can not reach SQL in connection" + e.getMessage());
            }
        }
    }
}