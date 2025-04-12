package com.shan.ms.pm.patientservice.mapper;

import com.shan.ms.pm.patientservice.dto.PatientRequestDTO;
import com.shan.ms.pm.patientservice.dto.PatientResponseDTO;
import com.shan.ms.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO to = new PatientResponseDTO();
        to.setId(patient.getId().toString());
        to.setName(patient.getName());
        to.setAddress(patient.getAddress());
        to.setEmail(patient.getEmail());
        to.setDateOfBirth(patient.getDateOfBirth().toString());
        return to;
    }

    public static Patient toPatient(PatientRequestDTO requestDTO) {
        Patient to = new Patient();
        to.setName(requestDTO.getName());
        to.setAddress(requestDTO.getAddress());
        to.setEmail(requestDTO.getEmail());
        to.setDateOfBirth(LocalDate.parse(requestDTO.getDateOfBirth()));
        to.setRegisteredDate(LocalDate.parse(requestDTO.getRegisteredDate()));
        return to;
    }

}
