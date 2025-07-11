package org.example;

import org.example.models.ConnectDB;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

class Member {

    public static int addMember(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Gender(F/M): ");
        char gender = scanner.nextLine().charAt(0);

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        //in one line recommended
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS Members (id INTEGER PRIMARY KEY, name TEXT, gender TEXT, age INTEGER, loaned_book_id INTEGER)";
                statement.executeUpdate(createTable);

                String insertSQL = "INSERT INTO Members (name, gender, age) VALUES (? , ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, String.valueOf(gender));
                preparedStatement.setInt(3, age);
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                int member_id = -1;
                if (resultSet.next()) {
                    member_id = resultSet.getInt(1);
                }
                System.out.println("Data inserted successfully.");
                System.out.println("New member ID: " + member_id);
                return member_id;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
        return 0;
    }


    public static void deleteMember(Scanner scanner) {
        System.out.print("Entre member ID: ");
        int member_id = scanner.nextInt();
        scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null) {
            try {
                String deleteSQL = ("DELETE FROM Members WHERE id =?");
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
                preparedStatement.setInt(1, member_id);
                int rows = preparedStatement.executeUpdate();

                if (rows > 0) {
                    System.out.println("ID: " + member_id + "Data deleted successfully.");
                } else {
                    System.out.println("ID: " + member_id + "Data not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }

    public static void showMember(Scanner scanner){
        System.out.print("Enter member ID: ");
        int member_id = scanner.nextInt();
        scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null)  {
            try {
                String selectSQL = "SELECT name, gender, age FROM Members WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setInt(1, member_id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String gender = resultSet.getString("gender");
                    int age = resultSet.getInt("age");
                    System.out.println("ID: " + member_id + ", Name: " + name + ", Gender: " + gender + ", Age: " + age);
                } else {
                    System.out.println("ID: " + member_id + "Data not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
                }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }
    public static void searchMember(Scanner scanner){
        System.out.print("name");
        String nameInput = scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null)  {
            try {
                String selectSQL = "SELECT id, name, gender, age FROM Members WHERE name LIKE ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setString(1, "%" + nameInput + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    int member_id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String gender = resultSet.getString("gender");
                    int age = resultSet.getInt("age");
                    System.out.println("ID: " + member_id + "Name: " + nameInput + " Gender: " + gender + " Age: " + age);
                }
                if  (!found) {
                    System.out.println("name: " + nameInput + "Data not found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }

    public void editMember(Scanner scanner){
        System.out.print("Enter member ID: ");
        int member_id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Gender(F/M): ");
        char gender = scanner.nextLine().charAt(0);
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        if (connection != null)  {
            try {
                String updateSQL = "UPDATE Members SET name = ?, gender = ?, age = ?  WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, String.valueOf(gender));
                preparedStatement.setInt(3, age);
                preparedStatement.setInt(4, member_id);
                int rows = preparedStatement.executeUpdate();
                if(rows>0){
                    System.out.println("ID: " + member_id + "Data modified successfully.");
                }  else {
                    System.out.println("ID: " + member_id + "Data not found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
                }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }
}
class Book {

    public static int addBook(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Number of pages: ");
        int numPages = scanner.nextInt();
        scanner.nextLine();
        //in one line recommended
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS Books (id INTEGER PRIMARY KEY, name TEXT, author TEXT, number_of_pages INTEGER, loaned_member_id INTEGER)";
                statement.executeUpdate(createTable);

                String insertSQL = "INSERT INTO Books (name, author, number_of_pages) VALUES (? , ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, author);
                preparedStatement.setInt(3, numPages);
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                int book_id = -1;
                if (resultSet.next()) {
                    book_id = resultSet.getInt(1);
                }
                System.out.println("Data inserted successfully.");
                System.out.println("New book ID: " + book_id);
                return book_id;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
        return 0;
    }


    public static void deleteBook(Scanner scanner) {
        System.out.print("Entre book ID: ");
        int book_id = scanner.nextInt();
        scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null) {
            try {
                String deleteSQL = ("DELETE FROM Books WHERE id =?");
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
                preparedStatement.setInt(1, book_id);
                int rows = preparedStatement.executeUpdate();

                if (rows > 0) {
                    System.out.println("ID: " + book_id + "Data deleted successfully.");
                } else {
                    System.out.println("ID: " + book_id + "Data not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }

    public static void showBook(Scanner scanner){
        System.out.print("Enter book ID: ");
        int book_id = scanner.nextInt();
        scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null)  {
            try {
                String selectSQL = "SELECT name, author, number_of_pages FROM Books WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setInt(1, book_id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String author = resultSet.getString("author");
                    int numPages = resultSet.getInt("number_of_pages");
                    System.out.println("ID: " + book_id + ", Name: " + name + ", Gender: " + author + ", Age: " + numPages);
                } else {
                    System.out.println("ID: " + book_id + "Data not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }
    public static void searchBook(Scanner scanner){
        System.out.print("name");
        String nameInput = scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null)  {
            try {
                String selectSQL = "SELECT id, name, author, number_of_pages FROM Books WHERE name LIKE ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setString(1, "%" + nameInput + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    int book_id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String author = resultSet.getString("author");
                    int numPages = resultSet.getInt("number_of_pages");
                    System.out.println("ID: " + book_id + ", Name: " + nameInput + ", Gender: " + author + ", Age: " + numPages);
                }
                if  (!found) {
                    System.out.println("name: " + nameInput + "Data not found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }

    public static void editBook(Scanner scanner){
        System.out.print("Enter book ID: ");
        int book_id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("author: ");
        String author = scanner.nextLine();
        System.out.print("Number of pages: ");
        int numPages = scanner.nextInt();
        scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        if (connection != null)  {
            try {
                String updateSQL = "UPDATE Books SET name = ?, author = ?, number_of_pages = ?  WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, author);
                preparedStatement.setInt(3, numPages);
                preparedStatement.setInt(4, book_id);
                int rows = preparedStatement.executeUpdate();
                if(rows>0){
                    System.out.println("ID: " + book_id + "Data modified successfully.");
                }  else {
                    System.out.println("ID: " + book_id + "Data not found.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }
}
class Shelve{

    public static void BookLoan(Scanner scanner) {
    }

    public static void Bookreturn(Scanner scanner) {
    }

    void bookLoan(Scanner scanner) {
        System.out.print("Enter book ID: ");
        int book_id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Member ID: ");
        int member_id = scanner.nextInt();
        scanner.nextLine();

        LocalDate date = LocalDate.now();
        LocalDate returnDate = date.plusDays(15);

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS Shelves (id INTEGER PRIMARY KEY, book_id INTEGER, member_id INTEGER, date TEXT, return_date TEXT)";
                statement.executeUpdate(createTable);

                String checkSQL = "SELECT * FROM Shelves WHERE book_id = ?";
                boolean recordExists = false;
                try (PreparedStatement checkStatement = connection.prepareStatement(checkSQL)){
                    checkStatement.setInt(1, book_id);
                    try(ResultSet resultset = checkStatement.getResultSet()){
                        if(resultset.next()){
                            recordExists = true;
                        }
                    }

                }
                if (recordExists) {
                    String updateSQL = "UPDATE Shelves SET member_id = ?, date = ?, return_date = ?  WHERE book_id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateSQL, Statement.RETURN_GENERATED_KEYS);
                    updateStatement.setInt(1, member_id);
                    updateStatement.setString(2, date.toString());
                    updateStatement.setString(3, returnDate.toString());
                    updateStatement.setInt(4, book_id);
                    int rows = updateStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("book ID: " + book_id + "loaned successfully to member ID: " + member_id);
                    }

                } else{
                    String insertSQL = "INSERT INTO Shelves (book_id, member_id, date, return_date) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
                    insertStatement.setInt(1, book_id);
                    insertStatement.setInt(2, member_id);
                    insertStatement.setString(3, date.toString());
                    insertStatement.setString(4, returnDate.toString());
                    insertStatement.executeUpdate();
                    System.out.println("book ID: " + book_id + "loaned successfully to member ID: " + member_id);
                }

                String updateSQL1 = "UPDATE Members SET loaned_book_id = ?  WHERE member_id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(updateSQL1);
                preparedStatement1.setInt(1, book_id);
                preparedStatement1.setInt(2, member_id);
                preparedStatement1.executeUpdate();
                ResultSet resultSet1 = preparedStatement1.getGeneratedKeys();
                System.out.println("Data inserted successfully in Members table.");

                String updateSQL2 = "UPDATE Books SET loaned_member_id = ?  WHERE book_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(updateSQL2);
                preparedStatement2.setInt(1, member_id);
                preparedStatement2.setInt(2, book_id);
                preparedStatement2.executeUpdate();
                ResultSet resultSet2 = preparedStatement2.getGeneratedKeys();
                System.out.println("Data inserted successfully in Books table.");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }
    void bookReturn(Scanner scanner) {
        System.out.print("Enter book ID: ");
        int book_id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Member ID: ");
        int member_id = scanner.nextInt();
        scanner.nextLine();

        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();

        if (connection != null) {
            try {
                String updateSQL = "UPDATE Shelves SET member_id = Null , data = Null, return_date = Null  WHERE book_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, book_id);
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                System.out.println("Data inserted successfully in Shelves table.");
                System.out.println("book ID: " + book_id + "return successfully.");

                String updateSQL1 = "UPDATE Members SET loaned_book_id = Null  WHERE member_id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(updateSQL1);
                preparedStatement1.setInt(1, member_id);
                preparedStatement1.executeUpdate();
                ResultSet resultSet1 = preparedStatement1.getGeneratedKeys();
                System.out.println("Data inserted successfully in Members table.");

                String updateSQL2 = "UPDATE Books SET loaned_member_id = Null  WHERE book_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(updateSQL2);
                preparedStatement2.setInt(1, book_id);
                preparedStatement2.executeUpdate();
                ResultSet resultSet2 = preparedStatement2.getGeneratedKeys();
                System.out.println("Data inserted successfully in Books table.");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
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
                        System.out.println("d. Search Member");
                        System.out.println("e. Back to Main Menu");
                        String memberChoice = scanner.nextLine();

                        switch (memberChoice) {
                            case "a":
                                Member.addMember(scanner);
                                break;
                            case "b":
                                Member.deleteMember(scanner);
                                break;
                            case "c":
                                Member.showMember(scanner);
                                break;
                            case "d":
                                Member.searchMember(scanner);
                                break;
                            case "e":
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
                        System.out.println("c. Show Book");
                        System.out.println("d. search Book");
                        System.out.println("e. Back to Main Menu");
                        String bookChoice = scanner.nextLine();

                        switch (bookChoice) {
                            case "a":
                               Book.addBook(scanner);
                                break;
                            case "b":
                                Book.deleteBook(scanner);
                                break;
                            case "c":
                                Book.showBook(scanner);
                                break;
                            case "d":
                                Book.searchBook(scanner);
                                break;
                            case "e":
                                bookMenu = false; // back to main menu
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }
                    }
                    break;

                case 3:
                    boolean loanMenu = true;
                    while (loanMenu) {
                        System.out.println("Loan Menu:");
                        System.out.println("a. loan Book");
                        System.out.println("b. return Book");
                        System.out.println("c. Back to Main Menu");
                        String bookChoice = scanner.nextLine();

                        switch (bookChoice) {
                            case "a":
                                Shelve.BookLoan(scanner);
                                break;
                            case "b":
                               Shelve.Bookreturn(scanner);
                                break;
                            case "c":
                                loanMenu = false; // back to main menu
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

        scanner.close();
    }
}