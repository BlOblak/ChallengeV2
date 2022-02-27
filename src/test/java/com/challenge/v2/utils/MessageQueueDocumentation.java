package com.challenge.v2.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageQueueDocumentation {
    private static final Logger log = LoggerFactory.getLogger(MessageQueueDocumentation.class);
    private final String outputDir;
    private final ObjectMapper objectMapper;

    public MessageQueueDocumentation(String outputDirectory) {
        this.outputDir = outputDirectory;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void generate(Message message, String exchange, String queue, Class baseClass) {
        this.generate(message, exchange, queue, baseClass, (String)null);
    }

    public void generate(Message message, String exchange, String queue, Class baseClass, String outputFileName) {
        try {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            String folderName;
            if (stackTraceElements.length > 3) {
                folderName = stackTraceElements[2].getMethodName();
                if (folderName.equals("generate")) {
                    folderName = stackTraceElements[3].getMethodName();
                }
            } else {
                folderName = "MqDocumentation-" + System.currentTimeMillis();
            }

            File file = new File(this.outputDir + "\\" + folderName);
            if (!file.exists() && file.mkdirs()) {
                log.info("Folder created");
            }

            if (outputFileName == null || outputFileName.equals("")) {
                outputFileName = "message";
            }

            MessageQueueDocumentation.DocumentationMq documentationObject = new MessageQueueDocumentation.DocumentationMq(message, exchange, queue, baseClass);
            String value = this.objectMapper.writeValueAsString(documentationObject);
            PrintWriter writer = new PrintWriter(file.getPath() + "\\" + outputFileName + ".md", "UTF-8");
            writer.println("```json");
            writer.println(value);
            writer.println("```");
            writer.close();
        } catch (IOException var12) {
            log.debug(var12.getMessage());
        }
    }

    static class DocumentationMq {
        String exchange;
        String queue;
        Object body;
        MessageProperties messageProperties;
        @JsonIgnore
        Message message;

        public DocumentationMq(Message msg, String exchange, String queue, Class baseClass) throws IOException {
            this.exchange = exchange;
            this.queue = queue;
            this.body = (new ObjectMapper()).readValue(msg.getBody(), baseClass);
            this.message = msg;
            this.messageProperties = this.message.getMessageProperties();
        }

        public String getExchange() {
            return this.exchange;
        }

        public String getQueue() {
            return this.queue;
        }

        public Object getBody() {
            return this.body;
        }

        public MessageProperties getMessageProperties() {
            return this.messageProperties;
        }

        public Message getMessage() {
            return this.message;
        }
    }
}
