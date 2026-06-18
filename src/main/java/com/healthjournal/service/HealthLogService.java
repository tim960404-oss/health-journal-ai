package com.healthjournal.service;

import com.healthjournal.model.HealthLog;
import com.healthjournal.repository.HealthLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HealthLogService {

    @Autowired
    private HealthLogRepository repository;

    @Autowired
    private DecisionTreeService decisionTreeService;

    public List<HealthLog> findAll() {
        return repository.findAll();
    }

    public HealthLog save(HealthLog log) {
        String risk = decisionTreeService.evaluate(log);
        log.setRiskLevel(risk);
        return repository.save(log);
    }

    public Optional<HealthLog> findById(Long id) {
        return repository.findById(id);
    }

    public HealthLog update(Long id, HealthLog updated) {
        return repository.findById(id).map(existing -> {
            existing.setLogDate(updated.getLogDate());
            existing.setSleepHours(updated.getSleepHours());
            existing.setSteps(updated.getSteps());
            existing.setMoodScore(updated.getMoodScore());
            String risk = decisionTreeService.evaluate(existing);
            existing.setRiskLevel(risk);
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Record not found: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public String getCurrentRisk() {
        List<HealthLog> logs = repository.findAll();
        if (logs.isEmpty()) return "NO_DATA";
        HealthLog latest = logs.get(logs.size() - 1);
        return decisionTreeService.evaluate(latest);
    }
}
