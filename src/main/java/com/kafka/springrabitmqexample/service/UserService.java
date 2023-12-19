package com.kafka.springrabitmqexample.service;

import com.kafka.springrabitmqexample.common.response.GenericResponse;
import com.kafka.springrabitmqexample.error.DatabaseAccessException;
import com.kafka.springrabitmqexample.error.UserCreateException;
import com.kafka.springrabitmqexample.error.UserNotFoundException;
import com.kafka.springrabitmqexample.model.User;
import com.kafka.springrabitmqexample.model.UserCreateRequest;
import com.kafka.springrabitmqexample.model.UserUpdateRequest;
import com.kafka.springrabitmqexample.repository.UserRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AmqpTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;

    @Value("${sample.rabbitmq.routingKey2}")
    String routingKey2;

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    @Value("${sample.rabbitmq.queue2}")
    String queueName2;

    public UserService(UserRepository userRepository, AmqpTemplate rabbitTemplate, DirectExchange exchange) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public GenericResponse<String> userCreateMessageSend(UserCreateRequest userCreateRequest) {
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, userCreateRequest);
        return new GenericResponse<>("User registration has been sent successfully", Boolean.TRUE);
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    private void userCreateMessageReceive(UserCreateRequest userCreateRequest) {
        try {
            userRepository.save(
                    new User(
                            userCreateRequest.name(),
                            userCreateRequest.surname(),
                            userCreateRequest.email()
                    )
            );
        } catch (Exception e) {
            throw new UserCreateException();
        }
    }

    public GenericResponse<User> userRead(Long id) {
        try {
            return new GenericResponse<>(userRepository.findById(id).orElseThrow(UserNotFoundException::new), Boolean.TRUE);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    public GenericResponse<List<User>> userReadAll() {
        try {
            return new GenericResponse<>(userRepository.findAll(), Boolean.TRUE);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }

    public GenericResponse<String> userUpdateMessageSend(UserUpdateRequest userUpdateRequest) {
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey2, userUpdateRequest);
        return new GenericResponse<>("User updates have been sent successfully", Boolean.TRUE);
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue2}")
    private void userUpdateMessageReceive(UserUpdateRequest userUpdateRequest) {
        try {
            User user = userRepository.findById(userUpdateRequest.id()).orElseThrow(UserNotFoundException::new);
            user.setName(userUpdateRequest.name());
            user.setSurname(userUpdateRequest.surname());
            user.setEmail(userUpdateRequest.email());
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserCreateException();
        }
    }

    public GenericResponse<String> userDelete(Long id){
        try {
            userRepository.delete(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
            return new GenericResponse<>("User was successfully deleted",Boolean.TRUE);
        } catch (Exception e){
            throw new UserNotFoundException();
        }
    }

}
