package com.scaler.emailservicecapstone.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendEmailProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendAcknowledgement(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
