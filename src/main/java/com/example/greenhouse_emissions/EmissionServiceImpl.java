package com.example.greenhouse_emissions;

import com.example.greenhouse_emissions.EmissionRecord;
import com.example.greenhouse_emissions.repository.EmissionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmissionServiceImpl implements EmissionService {

    @Autowired
    private EmissionRecordRepository emissionRecordRepository;

    @Override
    public List<EmissionRecord> getAllEmissions() {
        return emissionRecordRepository.findAll();
    }

    @Override
    public EmissionRecord getEmissionById(Long id) {
        Optional<EmissionRecord> record = emissionRecordRepository.findById(id);
        if (record.isPresent()) {
            return record.get();
        } else {
            throw new RuntimeException("Emission record not found for ID: " + id);
        }
    }

    @Override
    public EmissionRecord createEmission(EmissionRecord emissionRecord) {
        return emissionRecordRepository.save(emissionRecord);
    }

    @Override
    public EmissionRecord updateEmission(Long id, EmissionRecord updatedEmissionRecord) {
        EmissionRecord existingRecord = getEmissionById(id);
        existingRecord.setCategory(updatedEmissionRecord.getCategory());
        existingRecord.setYear(updatedEmissionRecord.getYear());
        existingRecord.setScenario(updatedEmissionRecord.getScenario());
        existingRecord.setValue(updatedEmissionRecord.getValue());
        existingRecord.setActualValue(updatedEmissionRecord.getActualValue());
        existingRecord.setDescription(updatedEmissionRecord.getDescription());
        return emissionRecordRepository.save(existingRecord);
    }

    @Override
    public void deleteEmission(Long id) {
        emissionRecordRepository.deleteById(id);
    }

    @Override
    public List<EmissionRecord> getEmissionsByCategory(String category) {
        return emissionRecordRepository.findByCategory(category);
    }
}
