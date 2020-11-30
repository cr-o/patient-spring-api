package com.project.codechallenge.patient;

import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Component
public class PatientRepositoryImpl implements PatientRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PatientRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Patient> find() {
        Query query = new Query();
        query.fields().include("recordNumber");
        return this.mongoTemplate.find(query, Patient.class);
    }

    public Patient findById(int id) {
        Query query = new Query(Criteria.where("_id").is(id));
        query.fields().include("recordNumber");
        return mongoTemplate.findOne(query, Patient.class, "patients");
    }

    public Patient modify(Patient patient) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(patient.getId()));
        Update update = new Update();
        update.set("firstName", patient.getFirstName());
        update.set("lastName", patient.getLastName());
        update.set("recordNumber", patient.getRecordNumber());
        update.set("state", patient.getState());
        FindAndModifyOptions.options().returnNew(true);
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Patient.class);
    }

    public List<PatientCount> groupByState() {
        GroupOperation group = Aggregation.group("state").count().as("count");
        ProjectionOperation project = Aggregation.project("count").and("state").previousOperation();
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "state");
        Aggregation aggregation = Aggregation.newAggregation(group, project, sort);
        return mongoTemplate.aggregate(aggregation, Patient.class, PatientCount.class).getMappedResults();
    }
}