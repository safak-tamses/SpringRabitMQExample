package com.kafka.springrabitmqexample.model;

public record UserUpdateRequest(Long id, String name, String surname, String email) {
}
