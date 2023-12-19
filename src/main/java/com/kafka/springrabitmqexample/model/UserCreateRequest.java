package com.kafka.springrabitmqexample.model;

public record UserCreateRequest(String name,String surname,String email) {
}
