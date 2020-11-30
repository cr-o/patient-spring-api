package com.project.codechallenge.patient;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "patients")
public class Patient implements Serializable {
    @Id
    @CsvBindByName(column = "id")
    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByName(column = "firstName")
    @CsvBindByPosition(position = 1)
    private String firstName;

    @CsvBindByName(column = "lastName")
    @CsvBindByPosition(position = 2)
    private String lastName;

    @CsvBindByName(column = "recordNumber", required = true) // required here doesn't actually make it required
    @CsvBindByPosition(position = 3, required = true) // required here works
    private int recordNumber;

    @CsvBindByName(column = "state")
    @CsvBindByPosition(position = 4)
    private String state;

    public Patient() { }

    public Patient(int id, String firstName, String lastName, int recordNumber, String state) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setRecordNumber(recordNumber);
        setState(state);
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getRecordNumber() {
        return this.recordNumber;
    }

    public String getState() {
        return this.state;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public void setState(String state) {
        this.state = state;
    }
}