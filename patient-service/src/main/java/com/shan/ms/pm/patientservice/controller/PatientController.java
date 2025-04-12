package com.shan.ms.pm.patientservice.controller;


import com.shan.ms.pm.patientservice.dto.PatientRequestDTO;
import com.shan.ms.pm.patientservice.dto.PatientResponseDTO;
import com.shan.ms.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Patient", description = "API for managing Patients")
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Get all patients")
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAll() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return  ResponseEntity.ok().body(patients);
    }

    @Operation(summary = "Create a patients")
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patient = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patient);
    }

    @Operation(summary = "Update a patients")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
            @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @Operation(summary = "Delete a patient")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deletePatient(@PathVariable UUID id) {
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
