package com.poc.alert_read_service.repository;

import com.poc.alert_read_service.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, String> {

    Alert findByAlertId(String alertId);

    List<Alert> findByHostname(String hostname);

    List<Alert> findBySeverity(String severity);

}