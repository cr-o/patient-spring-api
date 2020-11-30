package com.project.codechallenge.patient;

import com.project.codechallenge.CSVHelper;
import com.project.codechallenge.CSVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PatientController {
    final PatientRepository repository;

    final PatientRepositoryImpl patientRepositoryImpl;

    final CSVService csvService;

    public PatientController(PatientRepository repository, PatientRepositoryImpl patientRepositoryImpl, CSVService csvService) {
        this.repository = repository;
        this.patientRepositoryImpl = patientRepositoryImpl;
        this.csvService = csvService;
    }

    @PostMapping(value = "/patient")
    public ResponseEntity<Patient> insert(Patient patient) {
        repository.insert(patient);
        return ResponseEntity.ok().body(patient);
    }

    @PutMapping(value = "/patient")
    public ResponseEntity<?> modify(Patient patient) {
        Patient modifiedPatient = patientRepositoryImpl.modify(patient);
        if(modifiedPatient == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id does not exist.");
        }
        return ResponseEntity.ok().body(modifiedPatient);
    }

    @GetMapping(value = "/patients/search/findAll")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> allPatients = patientRepositoryImpl.find();
        return ResponseEntity.ok().body(allPatients);
    }

    @GetMapping(value = "/patients/search/findById")
    public ResponseEntity<?> findById(@RequestParam(value = "id", required = true) int id) {
        Patient idMatch = patientRepositoryImpl.findById(id);
        if(idMatch == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id does not exist.");
        }
        return ResponseEntity.ok().body(idMatch);
    }

    @GetMapping(value = "/patients/search/groupByState")
    public ResponseEntity<List<PatientCount>> groupByState() {
        List<PatientCount> groupedPatients =  patientRepositoryImpl.groupByState();
        return ResponseEntity.ok().body(groupedPatients);
    }

    @PostMapping(value = "/patients/uploadCSV")
    public ResponseEntity<?> uploadCSV(@RequestParam(value = "csvfile", required = true) MultipartFile file) {
        if (file == null || file.getSize() == 0 || !CSVHelper.isCSV(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty or non CSV file.");
        }
        try {
            csvService.savePatientCSV(file);
            return ResponseEntity.status(HttpStatus.OK).body("Success uploading CSV.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error reading file.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrectly formatted CSV file.");
        }
    }
}