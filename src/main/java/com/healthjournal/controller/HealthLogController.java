package com.healthjournal.controller;

import com.healthjournal.model.HealthLog;
import com.healthjournal.service.HealthLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/health-logs")
@CrossOrigin(origins = "*")
public class HealthLogController {

    @Autowired
    private HealthLogService service;

    @GetMapping
    public List<HealthLog> getAll() {
        return service.findAll();
    }

    @PostMapping
    public HealthLog create(@RequestBody HealthLog log) {
        return service.save(log);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthLog> update(@PathVariable Long id, @RequestBody HealthLog log) {
        try {
            return ResponseEntity.ok(service.update(id, log));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/risk")
    public Map<String, String> getRisk() {
        String risk = service.getCurrentRisk();
        return Map.of("risk_level", risk);
    }
}
