package com.poc.alert_read_service.repository;

import com.poc.alert_read_service.entity.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MetricsRepository extends JpaRepository<Metrics, Long> {

    Optional<Metrics> findByHostname(String hostname);

    List<Metrics> findByCpuUsageGreaterThan(BigDecimal cpuUsage);

    List<Metrics> findByMemoryUsageGreaterThan(BigDecimal memoryUsage);
}