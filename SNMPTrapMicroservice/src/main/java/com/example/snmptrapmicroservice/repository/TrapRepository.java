package com.example.snmptrapmicroservice.repository;

import com.example.snmptrapmicroservice.model.Trap;
import com.example.snmptrapmicroservice.model.Trap.TrapType;
import com.example.snmptrapmicroservice.model.Trap.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    @Query("SELECT t FROM Trap t WHERE t.host = :host AND t.timestamp BETWEEN :start AND :end")
    List<Trap> findByHostAndTimestampBetween(@Param("host") String host, 
                                              @Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);
    
    long countByTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    long countBySeverity(Severity severity);
    
    List<Trap> findTop10ByOrderByTimestampDesc();
    
    List<Trap> findBySeverityOrderByTimestampDesc(Severity severity);
}