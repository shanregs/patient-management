package com.shan.ms.pm.patientservice.service;

import com.shan.ms.pm.billing.model.BillingResponse;
import com.shan.ms.pm.patientservice.dto.PatientRequestDTO;
import com.shan.ms.pm.patientservice.dto.PatientResponseDTO;
import com.shan.ms.pm.patientservice.exception.EmailAlreadyExistsException;
import com.shan.ms.pm.patientservice.exception.PatientNotFoundException;
import com.shan.ms.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.shan.ms.pm.patientservice.kafka.PatientKafkaProducer;
import com.shan.ms.pm.patientservice.mapper.PatientMapper;
import com.shan.ms.pm.patientservice.model.Patient;
import com.shan.ms.pm.patientservice.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository  patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final PatientKafkaProducer patientKafkaProducer;
    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, PatientKafkaProducer patientKafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.patientKafkaProducer = patientKafkaProducer;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS = patients.stream().map(PatientMapper::toDTO).toList();
        return patientResponseDTOS;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        String email = patientRequestDTO.getEmail();

        if(emailExists(email)) {
            throw new EmailAlreadyExistsException("A Patient with this email "
            + email + " already exists. Use other email");
        }
        log.info("Received PatientRequestDTO : {}",patientRequestDTO);
        Patient newPatient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
        log.info("Saved new Patient  : {}",newPatient);
        BillingResponse response = billingServiceGrpcClient.createBillingRequest(newPatient.getId().toString(),
                newPatient.getName(), newPatient.getEmail());
        patientKafkaProducer.sendEvent(newPatient);
        log.info("Billing response : {}", response);
        return PatientMapper.toDTO(newPatient);
    }

    public boolean emailExists(String email) {
        boolean existsByEmail = patientRepository.existsByEmail(email);
        return existsByEmail;
    }

    public PatientResponseDTO updatePatient(
            UUID id,
            PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with the id - "+ id));
        String email = patientRequestDTO.getEmail();

        if(patientRepository.existsByEmailAndIdNot(email, id)) {
            throw new EmailAlreadyExistsException( "A patient with this email "+ email  + "already exists");
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deleteById(UUID id) {
        patientRepository.deleteById(id);
    }

}
