package com.shan.ms.pm.patientservice.kafka;

import com.shan.ms.pm.patient.events.PatientEvent;
import com.shan.ms.pm.patientservice.events.PatientEventsEnum;
import com.shan.ms.pm.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatientKafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(
            PatientKafkaProducer.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public PatientKafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType(PatientEventsEnum.PATIENT_CREATED.toString())

                .build();
        try{
            log.info("Sending patient event : {}", event.toString());
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {} - {}", event, e.getMessage());
        }
    }

}
