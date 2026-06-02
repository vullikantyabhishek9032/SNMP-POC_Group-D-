package com.example.snmptrapmicroservice.controller;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.snmptrapmicroservice.model.Trap;
import com.example.snmptrapmicroservice.model.Trap.Severity;
import com.example.snmptrapmicroservice.model.TrapDTO;
import com.example.snmptrapmicroservice.repository.TrapRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/traps")
@RequiredArgsConstructor
public class TrapController {
    
    private final TrapRepository trapRepository;
    
    @GetMapping
    public ResponseEntity<List<TrapDTO>> getAllTraps(
            @RequestParam(required = false) Severity severity,
            @RequestParam(required = false) String host,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {
        
        List<Trap> traps;
        
        if (severity != null) {
            traps = trapRepository.findBySeverity(severity);
        } else if (host != null) {
            traps = trapRepository.findByHost(host);
        } else if (from != null && to != null) {
            traps = trapRepository.findByTimestampBetween(from, to);
        } else {
            traps = trapRepository.findAll();
        }
        
        List<TrapDTO> dtos = traps.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
        
    }
    
    @GetMapping("/{trapId}")
    public ResponseEntity<TrapDTO> getTrap(@PathVariable Long trapId) {
        return trapRepository.findById(trapId)
            .map(trap -> ResponseEntity.ok(convertToDTO(trap)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/host/{host}")
    public ResponseEntity<List<TrapDTO>> getTrapsByHost(@PathVariable String host) {
        List<TrapDTO> dtos = trapRepository.findByHost(host)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<TrapDTO>> getTrapsBySeverity(@PathVariable Severity severity) {
        List<TrapDTO> dtos = trapRepository.findBySeverity(severity)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<TrapDTO>> getRecentTraps(@RequestParam(defaultValue = "10") int limit) {
        List<TrapDTO> dtos = trapRepository.findTop10ByOrderByTimestampDesc()
            .stream()
            .limit(limit)
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/stats/summary")
    public ResponseEntity<TrapStatistics> getStatistics() {
        TrapStatistics stats = new TrapStatistics();
        stats.setTotalTraps(trapRepository.count());
        stats.setCriticalCount(trapRepository.countBySeverity(Severity.CRITICAL));
        stats.setHighCount(trapRepository.countBySeverity(Severity.HIGH));
        stats.setMediumCount(trapRepository.countBySeverity(Severity.MEDIUM));
        stats.setLowCount(trapRepository.countBySeverity(Severity.LOW));
        return ResponseEntity.ok(stats);
    }
    
    @DeleteMapping("/{trapId}")
    public ResponseEntity<Void> deleteTrap(@PathVariable Long trapId) {
        if (trapRepository.existsById(trapId)) {
            trapRepository.deleteById(trapId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    private TrapDTO convertToDTO(Trap trap) {
        TrapDTO dto = new TrapDTO();
        dto.setTrapId(trap.getTrapId());
        dto.setTimestamp(trap.getTimestamp());
        dto.setHost(trap.getHost());
        dto.setCommunity(trap.getCommunity());
        dto.setTrapType(trap.getTrapType().name());
        dto.setVersion(trap.getVersion());
        dto.setSnmpTrapOid(trap.getSnmpTrapOid());
        dto.setSeverity(trap.getSeverity() != null ? trap.getSeverity().name() : null);
        dto.setMessage(trap.getMessage());
        dto.setProcessed(trap.getProcessed());
        
        if (trap.getVarbinds() != null) {
            dto.setVarbinds(trap.getVarbinds().stream()
                .map(v -> new TrapDTO.VarbindDTO(v.getOid(), v.getType(), v.getValue()))
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrapStatistics {
        private Long totalTraps;
        private Long criticalCount;
        private Long highCount;
        private Long mediumCount;
        private Long lowCount;
    }
}