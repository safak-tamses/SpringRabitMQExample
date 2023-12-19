package com.kafka.springrabitmqexample.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMqConfig {

    @Value("${sample.rabbitmq.exchange}")
    private String exchange;

    @Value("${sample.rabbitmq.queue}")
    private String queueName;

    @Value("${sample.rabbitmq.queue2}")
    private String queueName2;

    @Value("${sample.rabbitmq.routingKey}")
    private String routingKey;

    @Value("${sample.rabbitmq.routingKey2}")
    private String routingKey2;

    @Bean
    public DirectExchange rabbitMqExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue firstStepQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Queue secondStepQueue() {
        return new Queue(queueName2, false);
    }

    @Bean
    public Binding firstStepBinding(Queue firstStepQueue, DirectExchange rabbitMqExchange) {
        return BindingBuilder.bind(firstStepQueue).to(rabbitMqExchange).with(routingKey);
    }

    @Bean
    public Binding secondStepBinding(Queue secondStepQueue, DirectExchange rabbitMqExchange) {
        return BindingBuilder.bind(secondStepQueue).to(rabbitMqExchange).with(routingKey2);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
