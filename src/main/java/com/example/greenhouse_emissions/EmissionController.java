package com.example.greenhouse_emissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emissions")
public class EmissionController {

    @Autowired
    private EmissionService emissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmissionXmlParser emissionXmlParser;

    @Autowired
    private EmissionJsonParser emissionJsonParser;

    @GetMapping
    public ResponseEntity<List<EmissionRecord>> getAllEmissions() {
        if (!userService.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        return ResponseEntity.ok(emissionService.getAllEmissions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmissionRecord> getEmissionById(@PathVariable Long id) {
        if (!userService.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        EmissionRecord emissionRecord = emissionService.getEmissionById(id);
        return ResponseEntity.ok(emissionRecord);
    }

    @PostMapping
    public ResponseEntity<EmissionRecord> createEmission(@RequestBody EmissionRecord emissionRecord) {
        if (!userService.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        EmissionRecord createdRecord = emissionService.createEmission(emissionRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmissionRecord> updateEmission(@PathVariable Long id, @RequestBody EmissionRecord updatedEmissionRecord) {
        if (!userService.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        EmissionRecord updatedRecord = emissionService.updateEmission(id, updatedEmissionRecord);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmission(@PathVariable Long id) {
        if (!userService.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        emissionService.deleteEmission(id);
        return ResponseEntity.ok("Emission record deleted with ID: " + id);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<EmissionRecord>> getEmissionsByCategory(@PathVariable String category) {
        if (!userService.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        return ResponseEntity.ok(emissionService.getEmissionsByCategory(category));
    }

    @PostMapping("/parseXml")
    public ResponseEntity<String> parseXml() {
        if (!userService.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        String filePath = "src/main/resources/projections.xml";
        emissionXmlParser.parseAndSave(filePath);
        return ResponseEntity.ok("XML parsed and saved successfully.");
    }

    @PostMapping("/parseJson")
    public ResponseEntity<String> parseJson() {
        if (!userService.isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        String filePath = "src/main/resources/GreenhouseGasEmissions.json";
        emissionJsonParser.parseAndSave(filePath);
        return ResponseEntity.ok("JSON parsed and saved successfully.");
    }
}

