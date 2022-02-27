package com.challenge.v2.listener;

import com.challenge.Application;
import com.challenge.async.listener.AssetListener;
import com.challenge.config.MessageQueueConfig;
import com.challenge.domain.model.Measurement;
import com.challenge.v2.domain.model.MeasurementMock;
import com.challenge.v2.utils.MessageQueueDocumentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@SpringBootTest(classes = {Application.class})
public class AssetListenerTest {

    MessageQueueDocumentation mqdoc = new MessageQueueDocumentation("docs/assets/MQ/snippets");

    private ObjectMapper mapper;

    @Mock
    AssetListener assetListener;

    @BeforeEach
    public void init() {
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());;
    }

    @SneakyThrows
    @Test
    public void insertMeasurementByMessage() {

        Measurement messageInput = MeasurementMock.standardMeasurement(Date.from(Instant.now()));

        MessageProperties mp = new MessageProperties();
        mp.setContentType("application/json");
        mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        // build message
        Message message = new Message(mapper.writeValueAsString(messageInput).getBytes(StandardCharsets.UTF_8), mp);

        mqdoc.generate(message, "TestExchange", MessageQueueConfig.CHALLENGE_V2_MEASUREMENT_INSERT, Measurement.class);

        assetListener.insertMeasurement(message);

        verify(assetListener, times(1)).insertMeasurement(Mockito.any());
    }
}
