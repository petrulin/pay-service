package com.otus.payservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.queues.service-queue}")
    private String queue;
    @Value("${spring.rabbitmq.exchanges.service-exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.queues.service-answer-queue}")
    private String orderAnswerQueue;
    @Value("${spring.rabbitmq.exchanges.service-answer-exchange}")
    private String orderAnswerExchange;

    @Value("${spring.rabbitmq.queues.service-billing-queue}")
    private String billingQueue;
    @Value("${spring.rabbitmq.exchanges.service-billing-exchange}")
    private String billingExchange;


    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(queue, true, false, false);
    }

    @Bean
    public DirectExchange orderAnswerExchange() {
        return new DirectExchange(orderAnswerExchange, true, false);
    }

    @Bean
    public Queue orderAnswerQueue() {
        return new Queue(orderAnswerQueue, true, false, false);
    }

    @Bean
    public Binding queueSyncBinding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(queue);
    }
    @Bean
    public Binding orderAnswerQueueSyncBinding() {
        return BindingBuilder.bind(orderAnswerQueue()).to(orderAnswerExchange()).with(orderAnswerQueue);
    }

    @Bean
    public DirectExchange billingExchange() {
        return new DirectExchange(billingExchange, true, false);
    }

    @Bean
    public Queue billingQueue() {
        return new Queue(billingQueue, true, false, false);
    }
    @Bean
    public Binding billingQueueBinding() {
        return BindingBuilder.bind(billingQueue()).to(billingExchange()).with(billingQueue);
    }



}
