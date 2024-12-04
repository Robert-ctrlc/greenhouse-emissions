package com.example.greenhouse_emissions.repository;

import com.example.greenhouse_emissions.EmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmissionRecordRepository extends JpaRepository<EmissionRecord, Long> {
}
