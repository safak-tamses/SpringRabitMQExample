package com.kafka.springrabitmqexample.error;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User not found in the database!");
    }
}
