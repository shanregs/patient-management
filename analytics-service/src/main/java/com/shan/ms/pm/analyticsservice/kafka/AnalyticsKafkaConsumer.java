package com.shan.ms.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.shan.ms.pm.analyticsservice.config.KafkaConsumerProperties;
import com.shan.ms.pm.patient.events.PatientEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class AnalyticsKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsKafkaConsumer.class);
    private final KafkaConsumerProperties props;


    public AnalyticsKafkaConsumer(KafkaConsumerProperties props) {
        this.props = props;
    }


    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            String eventString = new String(event, StandardCharsets.UTF_8);
            log.info("Received Byte: {}", eventString);
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("Received Patient Event: [PatientId={},PatientName={},PatientEmail={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event {}", e.getMessage());
        }
    }
}



    /* @KafkaListener(topics = "#{@kafkaConsumerProperties.topic}",
             groupId = "#{@kafkaConsumerProperties.groupId}")
     public void consumeEvent(byte[] event) {
         try {
             log.info("Received Byte : ", event);
             PatientEvent patientEvent = PatientEvent.parseFrom(event);

             log.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={}]",
                     patientEvent.getPatientId(),
                     patientEvent.getName(),
                     patientEvent.getEmail());
         } catch (InvalidProtocolBufferException e) {
             log.error("Error deserializing event {}", e.getMessage());
         }
     }*/