package com.andrew.clinicalsapi.controllers;

import com.andrew.clinicalsapi.dto.ClinicalDataRequest;
import com.andrew.clinicalsapi.model.ClinicalData;
import com.andrew.clinicalsapi.model.Patient;
import com.andrew.clinicalsapi.repos.ClinicalDataRepository;
import com.andrew.clinicalsapi.repos.PatientRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {

    private final ClinicalDataRepository clinicalDataRepository;
    private final PatientRepository patientRepository;

    public ClinicalDataController(ClinicalDataRepository clinicalDataRepository, PatientRepository patientRepository) {
        this.clinicalDataRepository = clinicalDataRepository;
        this.patientRepository = patientRepository;
    }

    @RequestMapping(value = "/clinicals", method = RequestMethod.POST)
    public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request) {
       Patient patient = patientRepository.findById(request.getPatientId()).get();
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName(request.getComponentName());
        clinicalData.setComponentValue(request.getComponentValue());
        clinicalData.setPatient(patient);

        return clinicalDataRepository.save(clinicalData);
    }
}
