package org.example.dao;

import org.example.exception.MyConnectionException;
import org.example.exception.MySQLException;
import org.example.models.Books;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final Connection connection;

    public BookDAO(Connection connection) throws MyConnectionException {
        if (connection == null) {
            throw new MyConnectionException("connection is null");
        }
        this.connection = connection;
    }

    public boolean insertBook(Books book) throws MySQLException {
        try{
            Statement statement = connection.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS Books (id INTEGER, name TEXT, author TEXT, pages INTEGER, loaned_book_id INTEGER)";
            statement.executeUpdate(createTable);

            String insertSQL = "INSERT INTO Books (id, name, author, pages) VALUES (?, ? , ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setString(3, book.getBookAuthor());
            preparedStatement.setInt(4, book.getBookPages());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            throw new MySQLException("Error inserting book" + e.getMessage());
        }
    }

    public boolean updateBook(Books book) throws MySQLException {
        try{
            String updateSQL = "UPDATE Books SET name = ?, author = ?, pages = ?  WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getBookAuthor());
            preparedStatement.setInt(3, book.getBookPages());
            preparedStatement.setInt(4, book.getBookId());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new MySQLException("Error updating book" + e.getMessage());
        }
    }

    public boolean deleteBook(int bookId) throws MySQLException {
        try{
            String deleteSQL = "DELETE FROM Books WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, bookId);
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new MySQLException("Error deleting book" + e.getMessage());
        }
    }

    public List<Books> findByName(String bookName) throws MySQLException {
        List<Books> books = null;
        try{
            String selectSQL = "SELECT id, name, author, pages FROM books WHERE name LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, "%" + bookName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            books = new ArrayList<>();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String mainBookName = resultSet.getString("name");
                String bookAuthor = resultSet.getString("author");
                int bookPages = resultSet.getInt("pages");

                books.add(new Books(bookId, mainBookName, bookAuthor, bookPages));

            }
        } catch (SQLException e) {
            throw new MySQLException("Error finding book" + e.getMessage());
        }
        return books;
    }

    public List<Books> findAll() throws MySQLException {
        List<Books> books = null;
        try{
            String selectSQL = "SELECT * FROM books";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            books = new ArrayList<>();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String mainBookName = resultSet.getString("name");
                String bookAuthor = resultSet.getString("author");
                int bookPages = resultSet.getInt("pages");

                books.add(new Books(bookId, mainBookName, bookAuthor, bookPages));
            }
        } catch (SQLException e) {
            throw new MySQLException("Error finding book by name" + e.getMessage());
        }
        return books;
    }

    public Books findById(int bookId) throws  MySQLException {
        try {
                String selectSQL = "SELECT name, author, pages FROM Books WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setInt(1, bookId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String bookName = resultSet.getString("name");
                    String bookAuthor = resultSet.getString("author");
                    int bookPages = resultSet.getInt("pages");

                    Books book = new Books(bookId, bookName, bookAuthor, bookPages);
                    book.setBookName(bookName);
                    book.setBookAuthor(bookAuthor);
                    book.setBookPages(bookPages);

                    return book;
                } else {
                    System.out.println("ID: " + bookId + " Data not found.");
                }
        } catch (SQLException e) {
            throw new MySQLException("Error finding book by id" + e.getMessage());
        }
        return null;
    }
}

