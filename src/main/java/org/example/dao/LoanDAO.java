package org.example.dao;

import org.example.models.Books;
import org.example.models.Members;

import java.sql.*;
import java.time.LocalDate;

public class LoanDAO {
    private Connection connection;

    public LoanDAO(Connection connection) throws Exception {
        this.connection = connection;
    }
    public boolean saveLoan(Members member, Books book) throws SQLException {
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS Loans (book_id INTEGER, member_id INTEGER, loan_date TEXT, return_date TEXT, state TEXT)";
                statement.executeUpdate(createTable);

                String insertSQL = "INSERT INTO Loans (book_id, member_id, loan_date, return_date, state) VALUES (? , ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setInt(1, book.getBookId());
                preparedStatement.setInt(2, member.getMemberId());
                preparedStatement.setString(3, "Null");
                preparedStatement.setString(4, "Null");
                preparedStatement.setString(5, "loaned");
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
    }
    public boolean saveReturn(Members member, Books book) throws SQLException {
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();

                String insertSQL = "INSERT INTO Loans (book_id, member_id, loan_date, return_date, state) VALUES (? , ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setInt(1, book.getBookId());
                preparedStatement.setInt(2, member.getMemberId());
                preparedStatement.setString(3, "Null");
                LocalDate today = LocalDate.now();
                preparedStatement.setString(4, today.toString());
                preparedStatement.setString(5, "returned");
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
    }
}


