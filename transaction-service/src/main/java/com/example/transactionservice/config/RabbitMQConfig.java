package com.example.transactionservice.config;

import com.example.transactionservice.messaging.event.UserCreatedEvent;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_CREATED_QUEUE = "user.created.queue";
    public static final String USER_CREATED_KEY = "user.created";

    public static final String USER_CREATED_DLQ = "user.created.dlq";

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);
    }

    @Bean
    public Queue userCreatedQueue() {

        Map<String, Object> args = new HashMap<>();

        args.put("x-dead-letter-exchange", USER_EXCHANGE);
        args.put("x-dead-letter-routing-key", "user.created.dlq");

        return new Queue(USER_CREATED_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding bindingUserCreated(
            Queue userCreatedQueue,
            TopicExchange userExchange
    ) {
        return BindingBuilder
                .bind(userCreatedQueue)
                .to(userExchange)
                .with(USER_CREATED_KEY);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(USER_CREATED_DLQ);
    }

    @Bean
    public Binding bindingDLQ(
            Queue deadLetterQueue,
            TopicExchange userExchange
    ) {
        return BindingBuilder
                .bind(deadLetterQueue)
                .to(userExchange)
                .with("user.created.dlq");
    }

    // Definir el Mapper
    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("user.created", UserCreatedEvent.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

    // Convertir a json
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());
        return converter;
    }

    // Listener
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}