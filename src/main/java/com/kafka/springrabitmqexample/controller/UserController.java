package com.kafka.springrabitmqexample.controller;

import com.kafka.springrabitmqexample.common.response.GenericResponse;
import com.kafka.springrabitmqexample.model.User;
import com.kafka.springrabitmqexample.model.UserCreateRequest;
import com.kafka.springrabitmqexample.model.UserUpdateRequest;
import com.kafka.springrabitmqexample.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<GenericResponse<String>> userCreate(@RequestBody UserCreateRequest userCreateRequest) {
        return new ResponseEntity<>(userService.userCreateMessageSend(userCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<User>> userReadById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.userRead(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<User>>> userReadAll() {
        return new ResponseEntity<>(userService.userReadAll(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<GenericResponse<String>> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest) {
        return new ResponseEntity<>(userService.userUpdateMessageSend(userUpdateRequest), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<GenericResponse<String>> userDelete(@RequestParam Long id) {
        return new ResponseEntity<>(userService.userDelete(id), HttpStatus.OK);
    }
}
