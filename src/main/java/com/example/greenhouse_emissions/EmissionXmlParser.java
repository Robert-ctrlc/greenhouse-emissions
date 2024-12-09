package com.example.greenhouse_emissions;

import com.example.greenhouse_emissions.repository.EmissionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class EmissionXmlParser {

    @Autowired
    private EmissionRecordRepository emissionRecordRepository;

    public void parseAndSave(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            NodeList rows = document.getElementsByTagName("Row");
            System.out.println("Total ROW elements found: " + rows.getLength());

            int recordsSaved = 0;

            for (int i = 0; i < rows.getLength(); i++) {
                Node row = rows.item(i);

                if (row.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) row;

                  
                    String category = element.getElementsByTagName("Category__1_3").item(0).getTextContent();
                    String yearStr = element.getElementsByTagName("Year").item(0).getTextContent();
                    String scenario = element.getElementsByTagName("Scenario").item(0).getTextContent();
                    String valueStr = element.getElementsByTagName("Value").item(0).getTextContent();

                    
                    double value;
                    try {
                        value = Double.parseDouble(valueStr);
                        if (value <= 0) {
                            System.out.println("Skipping row with non-positive value: " + value);
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping row with invalid value: " + valueStr);
                        continue;
                    }

                    
                    if (!"WEM".equalsIgnoreCase(scenario)) {
                        System.out.println("Skipping row with non-WEM scenario: " + scenario);
                        continue;
                    }

                   
                    int year = Integer.parseInt(yearStr);
                    if (year != 2023) {
                        System.out.println("Skipping row with year: " + year);
                        continue;
                    }

                   
                    EmissionRecord record = new EmissionRecord();
                    record.setCategory(category);
                    record.setYear(year);
                    record.setScenario(scenario);
                    record.setValue(value);
                    record.setDescription("N/A"); 
                    emissionRecordRepository.save(record);
                    recordsSaved++;
                }
            }

            System.out.println("Parsing and saving completed. Total records saved: " + recordsSaved);

        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
