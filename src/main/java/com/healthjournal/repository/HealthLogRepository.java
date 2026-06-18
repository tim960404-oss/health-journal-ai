package com.healthjournal.repository;

import com.healthjournal.model.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthLogRepository extends JpaRepository<HealthLog, Long> {
}
