package com.kafka.springrabitmqexample.error;

public class UserCreateException extends RuntimeException{
    public UserCreateException() {
        super("User creating failed!");
    }
}
