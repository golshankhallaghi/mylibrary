package org.example;

import org.example.dao.ConnectionUtil;
import org.example.exception.MyConnectionException;
import org.example.exception.MySQLException;
import org.example.services.BooksService;
import org.example.services.LoanService;
import org.example.services.MembersService;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws MySQLException, MyConnectionException {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = ConnectionUtil.getConnection()) {

            MembersService membersService = new MembersService(connection);
            BooksService booksService = new BooksService(connection);
            LoanService loanService = new LoanService(connection);

            boolean exit = false;
            while (!exit) {
                System.out.println("Main Menu:");
                System.out.println("1. Member Options");
                System.out.println("2. Book Options");
                System.out.println("3. Loan Options");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int mainChoice = scanner.nextInt();
                scanner.nextLine();

                switch (mainChoice) {
                    case 1:
                        boolean memberMenu = true;
                        while (memberMenu) {
                            System.out.println("Member Menu:");
                            System.out.println("a. Add Member");
                            System.out.println("b. Delete Member");
                            System.out.println("c. Show Member");
                            System.out.println("d. edit Member");
                            System.out.println("e. Search Member");
                            System.out.println("f. Back to Main Menu");
                            String memberChoice = scanner.nextLine();

                            switch (memberChoice) {
                                case "a":
                                    System.out.print("Id: ");
                                    int memberId = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Name: ");
                                    String memberName = scanner.nextLine();
                                    System.out.print("Gender(F/M): ");
                                    String memberGender = scanner.nextLine();
                                    System.out.print("Age: ");
                                    int memberAge = scanner.nextInt();
                                    scanner.nextLine();
                                    membersService.addMember(memberId, memberName, memberGender, memberAge);
                                    break;
                                case "b":
                                    System.out.print("Enter member ID: ");
                                    int memberId2 = scanner.nextInt();
                                    scanner.nextLine();
                                    membersService.deleteMember(memberId2);
                                    break;
                                case "c":
                                    membersService.showMember();
                                    break;
                                case "d":
                                    System.out.print("member ID: ");
                                    int memberId3 = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Name: ");
                                    String memberName3 = scanner.nextLine();
                                    System.out.print("Gender(F/M): ");
                                    String memberGender3 = scanner.nextLine();
                                    System.out.print("Age: ");
                                    int memberAge3 = scanner.nextInt();
                                    scanner.nextLine();
                                    membersService.editMember(memberId3, memberName3, memberGender3, memberAge3);
                                    break;
                                case "e":
                                    System.out.print("Enter member name: ");
                                    String memberName4 = scanner.nextLine();
                                    membersService.searchMember(memberName4);
                                    break;
                                case "f":
                                    memberMenu = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                        }
                        break;

                    case 2:
                        boolean bookMenu = true;
                        while (bookMenu) {
                            System.out.println("Book Menu:");
                            System.out.println("a. Add Book");
                            System.out.println("b. Delete Book");
                            System.out.println("c. edit Book");
                            System.out.println("d. search Book");
                            System.out.println("e. show Book");
                            System.out.println("f. Back to Main Menu");
                            String bookChoice = scanner.nextLine();

                            switch (bookChoice) {
                                case "a":
                                    System.out.print("Id: ");
                                    int bookId = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Name: ");
                                    String bookName = scanner.nextLine();
                                    System.out.print("Author: ");
                                    String bookAuthor = scanner.nextLine();
                                    System.out.print("Number of pages: ");
                                    int bookPages = scanner.nextInt();
                                    scanner.nextLine();
                                    booksService.addBook(bookId, bookName, bookAuthor, bookPages);
                                    break;
                                case "b":
                                    System.out.print("Enter member ID: ");
                                    int bookId2 = scanner.nextInt();
                                    scanner.nextLine();
                                    booksService.deleteBook(bookId2);
                                    break;

                                case "c":
                                    System.out.print("book ID: ");
                                    int bookId3 = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Name: ");
                                    String bookName3 = scanner.nextLine();
                                    System.out.print("Author: ");
                                    String bookAuthor3 = scanner.nextLine();
                                    System.out.print("Pages: ");
                                    int bookPages3 = scanner.nextInt();
                                    scanner.nextLine();
                                    booksService.editBook(bookId3, bookName3, bookAuthor3, bookPages3);
                                    break;
                                case "d":
                                    System.out.print("Enter book name: ");
                                    String bookName4 = scanner.nextLine();
                                    booksService.searchBook(bookName4);
                                    break;
                                case "e":
                                    booksService.showBook();
                                    break;
                                case "f":
                                    bookMenu = false; // back to main menu
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                        }
                        break;
                    case 3:
                        boolean LoanMenu = true;
                        while (LoanMenu) {
                            System.out.println("Loan Menu:");
                            System.out.println("a. Loan books");
                            System.out.println("b. return books");
                            System.out.println("c. Back to Main Menu");
                            String loanChoice = scanner.nextLine();

                            switch (loanChoice) {
                                case "a":
                                    System.out.print("Enter member ID: ");
                                    int memberId5 = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Enter book ID: ");
                                    int bookId5 = scanner.nextInt();
                                    scanner.nextLine();
                                    loanService.loanBook(memberId5, bookId5);
                                    break;
                                case "b":
                                    System.out.print("Enter member ID: ");
                                    int memberId6 = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Enter book ID: ");
                                    int bookId6 = scanner.nextInt();
                                    scanner.nextLine();
                                    loanService.returnBook(memberId6, bookId6);
                                    break;
                                case "c":
                                    LoanMenu = false; // back to main menu
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                            }
                        }
                        break;
                    case 4:
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (MySQLException e) {
            System.out.println("Caught unexpected sql exception: " + e.getMessage());
            e.printStackTrace();
        } catch (MyConnectionException e) {
            System.out.println("Caught unexpected connection exception: " + e.getMessage());
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input please enter the correct data type");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e.getMessage());
            e.printStackTrace();
        }
        scanner.close();
    }
}