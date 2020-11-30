Coding Challenge

## Problem
For this challenge, you are tasked with augmenting a boilerplate Spring Boot application. The application uses an
embedded MongoDB. No front-end work is required. The tasks are as follows:

#### 1. Create a Domain Entity
Create a simple POJO that will be serialized to the DB. We want to store a *Patient* which should have the following
fields:

 * Id
 * First name
 * Last name
 * Medical record number
 * State (e.g. CA, MD, NY)
 
#### 2. Create a Spring Repository to Manage Patients
Create a Spring Data repository that exposes the Patient Mongo collection. We want to access/modify patients in the
following ways:

 * Insert a new patient
 * Modify an existing patient by id
 * View all patients
 * View a patient by id
 * View a patient by medical record number
 
**TIP**: With this completed you should be able to test out your application with a REST tester:

 * POST http://localhost:8080/patients (example URL) for creating a new patient
 * GET  http://localhost:8080/patients for listing patients
 * etc.
 
#### 3. Create a Patient Projection
Create a REST projection for patients which consists of:

 * Id
 * Medical record number
 
This projection should be usable in tandem with the View endpoints in the previous step.

#### 4. Create a Custom Patient Controller
Create a controller for a custom patient aggregation endpoint. This endpoint should return counts of patients per state.
Example:

```json
[
 { "state": "CA", "count": 3},
 { "state": "MD", "count": 4},
 { "state": "NY", "count": 1}
]
``` 

#### 5. Create a Bulk Patient CSV Upload Endpoint
Finally, add another endpoint to your newly created patient controller. This endpoint should:

 * Accept a CSV file (returning a 400 Bad Request if missing or badly formatted)
 * Insert patient rows found in the CSV into the database
 * Require that a patient row contain a medical record number in order to be inserted (other fields are optional)

## Setup

This project requires Java 11 or greater. We recommend running this application in an IDE such as IntelliJ. This can be
done by creating a Gradle run configuration with the tasks `clean build bootRun`