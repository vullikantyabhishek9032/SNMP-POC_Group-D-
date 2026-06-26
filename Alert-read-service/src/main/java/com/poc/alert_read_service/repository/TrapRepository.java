package com.poc.alert_read_service.repository;

import com.poc.alert_read_service.entity.Trap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrapRepository extends JpaRepository<Trap, String> {

    List<Trap> findByHost(String host);

    List<Trap> findBySeverity(String severity);

    List<Trap> findTop10ByOrderByReceivedAtDesc();
}