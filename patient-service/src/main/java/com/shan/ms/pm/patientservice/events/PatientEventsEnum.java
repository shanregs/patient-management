package com.shan.ms.pm.patientservice.events;

public enum PatientEventsEnum {
    PATIENT_CREATED("PATIENT_CREATED");

    PatientEventsEnum(String event) {
        this.event = event;
    }
    private String event;
}
