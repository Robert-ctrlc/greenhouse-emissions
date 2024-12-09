package com.example.greenhouse_emissions;

import com.example.greenhouse_emissions.repository.EmissionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Optional;

@Component
public class EmissionJsonParser {

    @Autowired
    private EmissionRecordRepository emissionRecordRepository;

    public void parseAndSave(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);

            int recordsUpdated = 0;
            int recordsAdded = 0;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String category = jsonObject.optString("category", "");
                String yearStr = jsonObject.optString("year", "");
                String scenario = jsonObject.optString("scenario", "");
                String valueStr = jsonObject.optString("value", "");

                if (valueStr == null || valueStr.isEmpty()) {
                    System.out.println("Skipping row with empty value");
                    continue;
                }

                double value;
                try {
                    value = Double.parseDouble(valueStr);
                    if (value <= 0 || !"Actual".equalsIgnoreCase(scenario)) {
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping row with invalid value: " + valueStr);
                    continue;
                }

                int year;
                try {
                    year = Integer.parseInt(yearStr);
                    if (year != 2023) {
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping row with invalid year: " + yearStr);
                    continue;
                }

                Optional<EmissionRecord> existingRecordOpt = emissionRecordRepository
                        .findByCategoryAndYear(category, year)
                        .stream().findFirst();

                if (existingRecordOpt.isPresent()) {
                    EmissionRecord existingRecord = existingRecordOpt.get();
                    existingRecord.setActualValue(value);
                    emissionRecordRepository.save(existingRecord);
                    recordsUpdated++;
                } else {
                    EmissionRecord newRecord = new EmissionRecord();
                    newRecord.setCategory(category);
                    newRecord.setYear(year);
                    newRecord.setScenario(scenario);
                    newRecord.setActualValue(value);
                    emissionRecordRepository.save(newRecord);
                    recordsAdded++;
                }
            }

            System.out.println("Parsing and saving completed. Records updated: " + recordsUpdated + ", Records added: " + recordsAdded);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
