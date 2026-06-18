package com.healthjournal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "health_logs")
public class HealthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "log_date", nullable = false)
    private String logDate;

    @Column(name = "sleep_hours", nullable = false)
    private Double sleepHours;

    @Column(name = "steps", nullable = false)
    private Integer steps;

    @Column(name = "mood_score", nullable = false)
    private Integer moodScore;

    @Column(name = "risk_level")
    private String riskLevel;

    public HealthLog() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogDate() { return logDate; }
    public void setLogDate(String logDate) { this.logDate = logDate; }

    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }

    public Integer getSteps() { return steps; }
    public void setSteps(Integer steps) { this.steps = steps; }

    public Integer getMoodScore() { return moodScore; }
    public void setMoodScore(Integer moodScore) { this.moodScore = moodScore; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
}
