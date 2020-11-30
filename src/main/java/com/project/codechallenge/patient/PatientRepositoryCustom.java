package com.project.codechallenge.patient;

import java.util.List;

public interface PatientRepositoryCustom {
    public List<Patient> find();
    public Patient findById(int id);
    public Patient modify(Patient patient);
    public List<PatientCount> groupByState();
}