package com.example.producertema2.configure;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "measurementassignment2_queue";
    public static final String EXCHANGE = "measurementassignment2_exchange";
    public static final String ROUTING_KEY = "measurementassignment2_routingKey";

    @Bean
    Queue queueA(){
        return new Queue(QUEUE);
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding binding(Queue queueA, TopicExchange topicExchange){
        return BindingBuilder.bind(queueA)
                .to(topicExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}


