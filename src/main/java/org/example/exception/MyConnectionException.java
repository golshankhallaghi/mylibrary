package org.example.exception;

public class MyConnectionException extends Exception {
    public MyConnectionException() {
        super("failed to connect!");
    }
    public MyConnectionException(String message) {
        super(message);
    }
    public MyConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}

