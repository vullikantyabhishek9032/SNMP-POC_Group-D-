package com.example.snmptrapmicroservice.service;

import com.example.snmptrapmicroservice.model.Trap;
import com.example.snmptrapmicroservice.model.Varbind;
import com.example.snmptrapmicroservice.repository.TrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.snmp4j.PDU;
import org.snmp4j.smi.VariableBinding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrapProcessingService {
    
    private final TrapRepository trapRepository;
    
    /**
     * Process incoming SNMP trap and store in MySQL database 
     */
    @Transactional
    public Trap processTrap(String host, String community, int pduType, 
                           String trapOid, Vector<VariableBinding> bindings, String version) {
        System.out.println("ENTERED processTrap()");
        Trap trap = new Trap();
        trap.setHost(host);
        trap.setCommunity(community);
        trap.setTrapType(mapTrapType(pduType));
        trap.setVersion("v" + version);
        trap.setSnmpTrapOid(trapOid);
        trap.setTimestamp(LocalDateTime.now());
        trap.setProcessed(false);
        
        // Set severity based on trap OID
        trap.setSeverity(determineSeverity(trapOid));
        
        // Set message based on trap type
        trap.setMessage(generateTrapMessage(trapOid, host));
        
        // Extract varbinds
        for (int i = 0; i < bindings.size(); i++) {
            VariableBinding vb = bindings.get(i);
            Varbind varbind = new Varbind();
            varbind.setTrap(trap);
            varbind.setOid(vb.getOid().toString());
            varbind.setType(vb.getVariable().getSyntaxString());
            varbind.setValue(vb.getVariable().toString());
            varbind.setIndexNum(i);
            trap.getVarbinds().add(varbind);
        }
        
        // Save to MySQL database
        System.out.println(" Before saving trap");

        Trap savedTrap = trapRepository.save(trap);
        log.info("Trap saved to database: ID={}, Host={}, Severity={}", 
            savedTrap.getTrapId(), host, savedTrap.getSeverity());
        System.out.println("After saving trap: " + savedTrap.getTrapId());
        return savedTrap;
    }
    
    private Trap.TrapType mapTrapType(int pduType) {
        if (pduType == PDU.TRAP) return Trap.TrapType.trap;
        if (pduType == PDU.NOTIFICATION) return Trap.TrapType.trap2;
        if (pduType == PDU.INFORM) return Trap.TrapType.inform;
        return Trap.TrapType.trap2;
    }
    
    private Trap.Severity determineSeverity(String trapOid) {
        if (trapOid == null) return Trap.Severity.MEDIUM;
        
        // Common trap OIDs and their severities
        if (trapOid.contains("memory") || trapOid.contains("disk")) {
            return Trap.Severity.CRITICAL;
        }
        if (trapOid.contains("cpu") || trapOid.contains("temperature")) {
            return Trap.Severity.HIGH;
        }
        if (trapOid.contains("linkDown") || trapOid.contains("linkUp")) {
            return Trap.Severity.HIGH;
        }
        if (trapOid.contains("authentication")) {
            return Trap.Severity.CRITICAL;
        }
        
        return Trap.Severity.MEDIUM;
    }
    
    private String generateTrapMessage(String trapOid, String host) {
        if (trapOid == null) return "SNMP trap received from " + host;
        
        if (trapOid.contains("memory")) return "Memory threshold exceeded on " + host;
        if (trapOid.contains("cpu")) return "CPU threshold exceeded on " + host;
        if (trapOid.contains("disk")) return "Disk space critical on " + host;
        if (trapOid.contains("linkDown")) return "Network interface down on " + host;
        if (trapOid.contains("linkUp")) return "Network interface up on " + host;
        
        return "SNMP trap received from " + host + " - OID: " + trapOid;
    }
}