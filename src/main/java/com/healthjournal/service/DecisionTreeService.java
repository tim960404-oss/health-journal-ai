package com.healthjournal.service;

import com.healthjournal.model.HealthLog;
import org.springframework.stereotype.Service;

@Service
public class DecisionTreeService {

    /**
     * 決策樹邏輯：
     * 第一層：判斷 sleep_hours
     *   < 6 小時 → 進入高風險分支
     *     第二層：steps < 3000 → 高風險 (HIGH)
     *     第二層：steps >= 3000 → 依 mood_score
     *       mood_score <= 4 → 高風險 (HIGH)
     *       mood_score > 4  → 中風險 (MEDIUM)
     *   >= 6 小時 → 進入低/中風險分支
     *     第二層：steps < 5000 → 中風險 (MEDIUM)
     *     第二層：steps >= 5000 → 依 mood_score
     *       mood_score >= 6 → 低風險 (LOW)
     *       mood_score < 6  → 中風險 (MEDIUM)
     */
    public String evaluate(HealthLog log) {
        double sleep = log.getSleepHours();
        int steps = log.getSteps();
        int mood = log.getMoodScore();

        if (sleep < 6.0) {
            if (steps < 3000) {
                return "HIGH";
            } else {
                return (mood <= 4) ? "HIGH" : "MEDIUM";
            }
        } else {
            if (steps < 5000) {
                return "MEDIUM";
            } else {
                return (mood >= 6) ? "LOW" : "MEDIUM";
            }
        }
    }
}
