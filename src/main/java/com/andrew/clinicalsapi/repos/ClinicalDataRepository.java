package com.andrew.clinicalsapi.repos;

import com.andrew.clinicalsapi.model.ClinicalData;
import org.springframework.data.repository.CrudRepository;

public interface ClinicalDataRepository extends CrudRepository<ClinicalData, Integer> {
}
