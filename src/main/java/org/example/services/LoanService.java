package org.example.services;
import org.example.dao.BookDAO;
import org.example.dao.LoanDAO;
import org.example.dao.MemberDAO;
import org.example.models.Books;
import org.example.models.Members;

import java.sql.Connection;
import java.sql.SQLException;

public class LoanService {
    private LoanDAO loanDAO;
    private MemberDAO memberDAO;
    private BookDAO bookDAO;
    Connection connection;

    public LoanService(Connection connection) throws Exception {
        this.connection = connection;
        this.loanDAO = new LoanDAO(connection);
        this.bookDAO = new BookDAO(connection);
        this.memberDAO = new MemberDAO(connection);
    }
    public void loanBook(int memberId, int bookId) {
        try {
            Books book = bookDAO.findById(bookId);
            Members member = memberDAO.findById(memberId);
            if (book != null && member != null) {
                boolean success = loanDAO.saveLoan(member, book);
                if (success) {
                    System.out.println("loaned information for member " + memberId + " and book " + bookId + "saved");
                }
            }
        }catch (SQLException e) {
                System.err.println("Error finding member or book: " + e.getMessage());
        }
    }
    public void returnBook(int memberId, int bookId){
        try{
            Books book = bookDAO.findById(bookId);
            Members member = memberDAO.findById(memberId);
            if (book != null && member != null) {
                boolean success = loanDAO.saveReturn(member, book);
                if (success) {
                    System.out.println("returned information for member " + memberId + " and book " + bookId + "saved");
                }
            }
        }catch (SQLException e) {
            System.err.println("Error finding member or book: " + e.getMessage());
        }
    }
}
