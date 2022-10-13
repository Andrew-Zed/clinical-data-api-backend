package com.andrew.clinicalsapi.controllers;

import com.andrew.clinicalsapi.model.ClinicalData;
import com.andrew.clinicalsapi.model.Patient;
import com.andrew.clinicalsapi.repos.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {

    private final PatientRepository patientRepository;
    Map<String, String> filters =  new HashMap<>();

    @Autowired
    PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public List<Patient> getPatients(){

        return (List<Patient>) patientRepository.findAll();
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
    public Patient getPatient(@PathVariable("id") int id) {

        return patientRepository.findById(id).get();
    }

    @RequestMapping(value = "/patients", method = RequestMethod.POST)
    public Patient savePatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @RequestMapping(value = "/patients/analyze/{id}", method = RequestMethod.GET)
    public Patient analyze(@PathVariable("id") int id) {
        Patient patient = patientRepository.findById(id).get();
        List<ClinicalData> clinicalData = patient.getClinicalData();
        List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
        for (ClinicalData eachEntry : duplicateClinicalData) {

                    if (filters.containsKey(eachEntry.getComponentName())) {
                        clinicalData.remove(eachEntry);
                        continue;
                    } else {
                        filters.put(eachEntry.getComponentName(), null);
                    }

                    if (eachEntry.getComponentName().equals("hw")) {
                        String[] heightAndWeight = eachEntry.getComponentValue().split("/");

                        if (heightAndWeight != null && heightAndWeight.length > 1) {
                            float heightInMetres = Float.parseFloat(heightAndWeight[0]) * 0.4536F;
                            float bmi = Float.parseFloat(heightAndWeight[1]) / (heightInMetres * heightInMetres);
                            ClinicalData bmiData = new ClinicalData();
                            bmiData.setComponentName("bmi");
                            bmiData.setComponentValue(Float.toString(bmi));
                            clinicalData.add(bmiData);
                        }
                }
        }
        filters.clear();
        return patient;
    }
}
