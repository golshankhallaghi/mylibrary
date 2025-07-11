package org.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private Connection connection;

    public ConnectDB(){
        String url = "jdbc:sqlite:mylibrary.db";
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("connection established");
        } catch (SQLException e) {
            System.out.println("error connecting");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;

    }
    public void closeConnection() {
        try {
            if(connection!=null){
                System.out.println("connection closed");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
