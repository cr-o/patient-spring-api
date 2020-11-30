package com.project.codechallenge;

import com.project.codechallenge.patient.Patient;
import com.project.codechallenge.patient.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    final PatientRepository repository;

    public CSVService(PatientRepository repository) {
        this.repository = repository;
    }

    public void savePatientCSV(MultipartFile file) throws IOException, RuntimeException {
        List<Patient> patientRecords = CSVHelper.csvToPatients(file);
        repository.saveAll(patientRecords);
    }
}