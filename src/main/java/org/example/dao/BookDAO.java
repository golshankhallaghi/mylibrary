package org.example.dao;

import org.example.models.Books;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection connection;

    public BookDAO(Connection connection) throws Exception {
        this.connection = connection;
    }

    public boolean insertBook(Books book) throws SQLException {
        if (connection != null) {
            try {
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
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
    }

    public boolean updateBook(Books book) throws SQLException {
        if (connection != null) {
            try {
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
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
    }

    public boolean deleteBook(int bookId) throws SQLException {
        if (connection != null) {
            try {
                String deleteSQL = "DELETE FROM Books WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
                preparedStatement.setInt(1, bookId);
                int rows = preparedStatement.executeUpdate();

                return rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
    }

    public List<Books> findByName(String bookName) throws SQLException {
        List<Books> books = null;
        if (connection != null) {
            try {
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
                if (books.isEmpty()) {
                    System.out.println("ID: " + bookName + " Data not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
        return books;
    }

    public List<Books> findAll() throws SQLException {
        List<Books> books = null;
        if (connection != null) {
            try {
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
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
        return books;
    }

    public Books findById(int bookId) throws SQLException {
        if (connection != null) {
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
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
        return null;
    }
}

