package com.example.greenhouse_emissions.repository;

import com.example.greenhouse_emissions.EmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmissionRecordRepository extends JpaRepository<EmissionRecord, Long> {
    List<EmissionRecord> findByCategoryAndYear(String category, Integer year);

    List<EmissionRecord> findByCategory(String category);
}

