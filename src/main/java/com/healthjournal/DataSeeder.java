package com.healthjournal;

import com.healthjournal.model.HealthLog;
import com.healthjournal.repository.HealthLogRepository;
import com.healthjournal.service.DecisionTreeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final HealthLogRepository repository;
    private final DecisionTreeService decisionTree;

    public DataSeeder(HealthLogRepository repository, DecisionTreeService decisionTree) {
        this.repository = repository;
        this.decisionTree = decisionTree;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;

        Object[][] seeds = {
            {"2026-05-01", 4.5, 1200, 2},
            {"2026-05-02", 5.0, 2800, 3},
            {"2026-05-03", 5.5, 3200, 5},
            {"2026-05-04", 4.0, 1500, 1},
            {"2026-05-05", 5.2, 2000, 4},
            {"2026-05-06", 4.8, 900,  2},
            {"2026-05-07", 6.5, 4500, 5},
            {"2026-05-08", 7.0, 3800, 4},
            {"2026-05-09", 6.2, 4800, 5},
            {"2026-05-10", 6.8, 4200, 3},
            {"2026-05-11", 5.8, 5200, 5},
            {"2026-05-12", 6.0, 4600, 4},
            {"2026-05-13", 7.5, 8000, 7},
            {"2026-05-14", 8.0, 9500, 8},
            {"2026-05-15", 7.8, 7200, 6},
            {"2026-05-16", 8.5, 8800, 9},
            {"2026-05-17", 7.2, 6500, 7},
            {"2026-05-18", 7.0, 7800, 8},
            {"2026-05-19", 8.2, 9000, 7},
            {"2026-05-20", 7.5, 6800, 6},
            {"2026-05-21", 6.5, 5500, 5},
            {"2026-05-22", 5.0, 2500, 3},
            {"2026-05-23", 4.5, 1800, 2},
            {"2026-05-24", 7.0, 7000, 7},
            {"2026-05-25", 8.0, 8500, 8},
        };

        for (Object[] row : seeds) {
            HealthLog log = new HealthLog();
            log.setLogDate((String) row[0]);
            log.setSleepHours(((Number) row[1]).doubleValue());
            log.setSteps(((Number) row[2]).intValue());
            log.setMoodScore(((Number) row[3]).intValue());
            log.setRiskLevel(decisionTree.evaluate(log));
            repository.save(log);
        }
        System.out.println("✅ 種子資料已插入（25 筆）");
    }
}
