package com.project.codechallenge.patient;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryRestResource(excerptProjection = PatientProjection.class)
public interface PatientRepository extends MongoRepository<Patient, Integer> {
    public List<Patient> findAllByRecordNumber(@Param("recordNumber")int recordNumber);
}