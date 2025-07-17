package org.example.exception;

public class MySQLException extends Exception {
    public MySQLException() {
        super("Something went wrong about the mySQLException");
    }
    public MySQLException(String message) {
        super(message);
    }
    public MySQLException(String message, Throwable cause) {
        super(message, cause);
    }

}
