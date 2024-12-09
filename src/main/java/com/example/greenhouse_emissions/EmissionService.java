package com.example.greenhouse_emissions;

import com.example.greenhouse_emissions.EmissionRecord;

import java.util.List;

public interface EmissionService {
    List<EmissionRecord> getAllEmissions();

    EmissionRecord getEmissionById(Long id);

    EmissionRecord createEmission(EmissionRecord emissionRecord);

    EmissionRecord updateEmission(Long id, EmissionRecord updatedEmissionRecord);

    void deleteEmission(Long id);

    List<EmissionRecord> getEmissionsByCategory(String category);
}



