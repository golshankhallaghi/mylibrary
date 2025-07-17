package org.example.services;

import org.example.dao.BookDAO;
import org.example.models.Books;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BooksService {
    private BookDAO bookDAO;
    private Connection connection;

    public BooksService(Connection connection) throws Exception {
        try{
            this.connection = connection;
            this.bookDAO = new BookDAO(connection);
    }catch(SQLException e){
        e.printStackTrace();}
    }

    public void addBook(int bookId, String bookName, String bookAuthor, int bookPages) {
        Books book = new Books(bookId, bookName, bookAuthor, bookPages);
        try {
            boolean success = bookDAO.insertBook(book);
            if (success) {
                System.out.println("Book: " + bookId + " added successfully");
            } else {
                System.out.println("Failed to add book:" + bookId);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting book: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId) {
        try {
            boolean success = bookDAO.deleteBook(bookId);
            if (success) {
                System.out.println("Book: " + bookId + " deleted successfully");
            } else {
                System.out.println("Failed to delete book: " + bookId);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

    public void showBook() {
        try{
            List<Books> books = bookDAO.findAll();
            if (books.isEmpty()) {
                System.out.println("Book not found");
            } else {
                for  (Books book : books) {
                    System.out.println("id: " + book.getBookId() + " name: " + book.getBookName() + " author: " + book.getBookAuthor() + " pages: " + book.getBookPages());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding book: " + e.getMessage());
        }
    }

    public void searchBook(String bookName) {
        try {
            List<Books> books = bookDAO.findByName(bookName);
            if (books.isEmpty()) {
                System.out.println("book not found");
            } else {
                for  (Books book : books) {
                    System.out.println("id: " + book.getBookId() + " name: " + book.getBookName() + " author: " + book.getBookAuthor() + " pages: " + book.getBookPages());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding book: " + e.getMessage());
        }
    }

    public void editBook(int bookId, String bookName, String bookAuthor, int bookPages) {
        Books book = new Books(bookId, bookName, bookAuthor, bookPages);
        book.setBookId(bookId);
        book.setBookName(bookName);
        book.setBookAuthor(bookAuthor);
        book.setBookPages(bookPages);
        try {
            boolean success = bookDAO.updateBook(book);
            if (success) {
                System.out.println("Book: " + bookId + " edited successfully");
            } else {
                System.out.println("Failed to edit book: " + bookId);
            }
        } catch (SQLException e) {
            System.err.println("Error editing book: " + e.getMessage());
        }
    }

}
