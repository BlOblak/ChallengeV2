package com.challenge.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
@Slf4j
public class MessageQueueConfig {

    @Value("${spring.rabbitmq.listener.simple.prefetch}")
    private Integer prefetchCount;

    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private Integer concurrentConsumers;

    // Queues
    public static final String CHALLENGE_V2_MEASUREMENT_INSERT = "challenge.v2.measurement.insert";

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(final ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(prefetchCount);
        factory.setConcurrentConsumers(concurrentConsumers);
        final ExponentialBackOff backOff = new ExponentialBackOff(5000, 1.5);
        backOff.setMaxInterval(30000);
        backOff.setMaxElapsedTime(180000);
        factory.setRecoveryBackOff(backOff);
        return factory;
    }

    @Bean
    Channel channel(){
        try {
            log.debug("MessageQueueConfig Channel bean invoked");
            com.rabbitmq.client.ConnectionFactory factory;
            Connection connection;
            Channel channel;
            factory = new com.rabbitmq.client.ConnectionFactory();
            factory.setHost(System.getenv("IN2_RABBITMQ_HOST"));
            factory.setPort(Integer.parseInt(System.getenv("IN2_RABBITMQ_PORT")));
            factory.setUsername(System.getenv("IN2_RABBITMQ_USERNAME"));
            factory.setPassword(System.getenv("IN2_RABBITMQ_PASSWORD"));
            connection = factory.newConnection();
            channel = connection.createChannel();

            return channel;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
