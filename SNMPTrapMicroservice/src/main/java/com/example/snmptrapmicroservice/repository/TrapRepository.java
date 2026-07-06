package com.example.snmptrapmicroservice.repository;

import com.example.snmptrapmicroservice.model.Trap;
import com.example.snmptrapmicroservice.model.Trap.TrapType;
import com.example.snmptrapmicroservice.model.Trap.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrapRepository extends JpaRepository<Trap, Long> {

    List<Trap> findByHost(String host);

    List<Trap> findBySeverity(Severity severity);

    List<Trap> findByTrapType(TrapType trapType);

    List<Trap> findByProcessed(Boolean processed);

    List<Trap> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    List<Trap> findByProcessedFalse();

    long countByProcessedFalse();

    long deleteByTimestampBefore(LocalDateTime time);

    // Optional methods
    long countByTimestampBetween(LocalDateTime start, LocalDateTime end);

    long countBySeverity(Severity severity);

    List<Trap> findTop10ByOrderByTimestampDesc();

    List<Trap> findBySeverityOrderByTimestampDesc(Severity severity);
}
