package com.challenge.async.listener;

import com.challenge.config.MessageQueueConfig;
import com.challenge.domain.model.Measurement;
import com.challenge.service.AssetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AssetListener {

    @Autowired
    AssetService assetService;

    @RabbitListener(queues = {MessageQueueConfig.CHALLENGE_V2_MEASUREMENT_INSERT})
    public void insertMeasurement(Message message){
        try{
            log.info("AssetListener -> insertMeasurement invoked");
            // Parse message
            String messageContent = new String(message.getBody(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Measurement measurement = null;
            try {
                measurement = mapper.readValue(messageContent, Measurement.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            // If parsed, insert
            if(measurement != null)
                assetService.insert(measurement);
        }
        catch (Exception e) {
            log.error("AssetListener -> insertMeasurement exception: " + e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
