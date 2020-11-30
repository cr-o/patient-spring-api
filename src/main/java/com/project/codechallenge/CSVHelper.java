package com.project.codechallenge;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.project.codechallenge.patient.Patient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

public class CSVHelper {
    public static String[] CSV_HEADER = { "id", "firstName", "lastName", "recordNumber", "state" };

    public static boolean isCSV(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1].equals("csv");
    }

    public static List<Patient> csvToPatients(MultipartFile file) throws IOException, RuntimeException {
        InputStream inputStream = file.getInputStream();
        Reader fileReader = new InputStreamReader(inputStream);
        ColumnPositionMappingStrategy<Patient> mappingStrategy = new ColumnPositionMappingStrategy<Patient>();
        mappingStrategy.setColumnMapping(CSV_HEADER);
        mappingStrategy.setType(Patient.class);
        CsvToBean<Patient> csvToBean =  new CsvToBeanBuilder<Patient>(fileReader).withMappingStrategy(mappingStrategy).withSkipLines(1).withIgnoreLeadingWhiteSpace(true).build();
        List<Patient> patientList = csvToBean.parse();
        fileReader.close();
        return patientList;
    }
}