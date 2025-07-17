package org.example.services;

import org.example.dao.BookDAO;
import org.example.dao.LoanDAO;
import org.example.dao.MemberDAO;
import org.example.exception.MyConnectionException;
import org.example.exception.MySQLException;
import org.example.models.Books;
import org.example.models.Members;

import java.sql.Connection;
import java.sql.SQLException;

public class LoanService {
    Connection connection;
    private final LoanDAO loanDAO;
    private final MemberDAO memberDAO;
    private final BookDAO bookDAO;

    public LoanService(Connection connection) throws MyConnectionException {
        try {
            this.connection = connection;
            this.loanDAO = new LoanDAO(connection);
            this.bookDAO = new BookDAO(connection);
            this.memberDAO = new MemberDAO(connection);
        } catch (Exception e) {
            throw new MyConnectionException("error initializing DAOs: " + e.getMessage());
        }
    }

    public void loanBook(int memberId, int bookId) throws MySQLException {
        Books book = bookDAO.findById(bookId);
        Members member = memberDAO.findById(memberId);
        if (book == null){
            throw new MySQLException("book not found");
        }
        if (member == null) {
            throw new MySQLException("member not found");
        }
        boolean success = loanDAO.saveLoan(member, book);
        if (success) {
            System.out.println("loaned information for member " + memberId + " and book " + bookId + "saved");
        }
    }

    public void returnBook(int memberId, int bookId) throws MySQLException {
        Books book = bookDAO.findById(bookId);
        Members member = memberDAO.findById(memberId);
        if (book == null){
            throw new MySQLException("book not found");
        }
        if(member == null) {
            throw new MySQLException("member not found");
        }
        boolean success = loanDAO.saveReturn(member, book);
        if (success) {
            System.out.println("returned information for member " + memberId + " and book " + bookId + "saved");
        }
    }
}

