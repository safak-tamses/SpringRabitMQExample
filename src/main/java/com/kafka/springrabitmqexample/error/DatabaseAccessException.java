package com.kafka.springrabitmqexample.error;

public class DatabaseAccessException extends RuntimeException{
    public DatabaseAccessException() {
        super("Database access exception!");
    }
}
