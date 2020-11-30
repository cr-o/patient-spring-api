package com.project.codechallenge.patient;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "patientProjection", types = { Patient.class })
public interface PatientProjection {
    int getId();
    int getRecordNumber();
}