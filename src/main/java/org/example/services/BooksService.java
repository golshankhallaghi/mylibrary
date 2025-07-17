package org.example.services;

import org.example.dao.BookDAO;
import org.example.exception.MyConnectionException;
import org.example.exception.MySQLException;
import org.example.models.Books;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BooksService {
    Connection connection;
    private final BookDAO bookDAO;

    public BooksService(Connection connection) throws MyConnectionException {
        try {
            this.connection = connection;
            this.bookDAO = new BookDAO(connection);
        }catch (Exception e) {
            throw new MyConnectionException("error connecting to book dao" + e.getMessage());
        }

    }

    public void addBook(int bookId, String bookName, String bookAuthor, int bookPages) throws MySQLException {
        Books book = new Books(bookId, bookName, bookAuthor, bookPages);
        boolean success = bookDAO.insertBook(book);
        if (success) {
            System.out.println("Book: " + bookId + " added successfully");
        } else {
            System.out.println("Failed to add book:" + bookId);
        }
    }

    public void deleteBook(int bookId) throws MySQLException {
        boolean success = bookDAO.deleteBook(bookId);
        if (success) {
            System.out.println("Book: " + bookId + " deleted successfully");
        } else {
            System.out.println("Failed to delete book: " + bookId);
        }
    }

    public void showBook() throws MySQLException {
        List<Books> books = bookDAO.findAll();
        if (books.isEmpty()) {
            System.out.println("Book not found");
        } else {
            for (Books book : books) {
                System.out.println("id: " + book.getBookId() + " name: " + book.getBookName() + " author: " + book.getBookAuthor() + " pages: " + book.getBookPages());
            }
        }
    }

    public void searchBook(String bookName) throws MySQLException {
        List<Books> books = bookDAO.findByName(bookName);
        if (books.isEmpty()) {
            System.out.println("book: " + bookName + " Data not found.");
        } else {
            for (Books book : books) {
                System.out.println("id: " + book.getBookId() + " name: " + book.getBookName() + " author: " + book.getBookAuthor() + " pages: " + book.getBookPages());
            }
        }
    }

    public void editBook(int bookId, String bookName, String bookAuthor, int bookPages) throws MySQLException {
        Books book = new Books(bookId, bookName, bookAuthor, bookPages);
        book.setBookId(bookId);
        book.setBookName(bookName);
        book.setBookAuthor(bookAuthor);
        book.setBookPages(bookPages);
        boolean success = bookDAO.updateBook(book);
        if (success) {
            System.out.println("Book: " + bookId + " edited successfully");
        } else {
            System.out.println("Failed to edit book: " + bookId);
        }
    }
}
