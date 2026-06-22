package com.example.snmpmicroservice.service;

import com.example.snmpmicroservice.Exception.SnmpTimeoutException;
import com.example.snmpmicroservice.config.SnmpConfig;
import com.example.snmpmicroservice.model.SystemMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnmpService {
    
    private final SnmpConfig snmpConfig;
    
    private static final String MEM_TOTAL_OID = ".1.3.6.1.4.1.2021.4.5.0";
    private static final String MEM_AVAILABLE_OID = ".1.3.6.1.4.1.2021.4.6.0";
    private static final String CPU_IDLE_OID = ".1.3.6.1.4.1.2021.11.11.0";
    private static final String UPTIME_OID = ".1.3.6.1.2.1.1.3.0";
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public SystemMetrics querySnmpMetrics(String hostname) {
        SystemMetrics metrics = new SystemMetrics();
        metrics.setHostname(hostname);
        metrics.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        try {
            TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
            Snmp snmp = new Snmp(transport);
            transport.listen();
            
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(snmpConfig.getCommunityPublic()));
            target.setAddress(GenericAddress.parse(
                "udp:" + snmpConfig.getManagerHost() + "/" + snmpConfig.getManagerPort()));
            target.setVersion(SnmpConstants.version2c);
            target.setTimeout(snmpConfig.getTimeout());
            target.setRetries(snmpConfig.getRetries());
            
            Long memTotal = getSnmpLong(snmp, target, MEM_TOTAL_OID);
            Long memAvailable = getSnmpLong(snmp, target, MEM_AVAILABLE_OID);
            
            if (memTotal != null && memAvailable != null) {
                metrics.setMemoryTotal(memTotal * 1024);
                metrics.setMemoryAvailable(memAvailable * 1024);
                metrics.setMemoryUsed(memTotal - memAvailable);
                metrics.setMemoryUsage(calculatePercentage(memAvailable, memTotal));
            }
            
            Long cpuIdle = getSnmpLong(snmp, target, CPU_IDLE_OID);
            if (cpuIdle != null) {
                metrics.setCpuUsage(100.0 - cpuIdle.doubleValue());
            }
            
            Long uptime = getSnmpLong(snmp, target, UPTIME_OID);
            if (uptime != null) {
                metrics.setUptime(uptime / 100);
            }
            
            snmp.close();
            
        } catch (IOException e) {
            log.error("SNMP query failed for {}", hostname, e);
            throw new SnmpTimeoutException("SNMP Timeout for host: " + hostname);
        }
        
        return metrics;
    }
    
    private Long getSnmpLong(Snmp snmp, CommunityTarget target, String oid) {
        try {
            PDU pdu = new PDU();
            pdu.add(new VariableBinding(new OID(oid)));
            pdu.setType(PDU.GET);
            
            ResponseEvent response = snmp.send(pdu, target);
            if (response != null && response.getResponse() != null && 
                response.getResponse().size() > 0) {
                VariableBinding vb = response.getResponse().get(0);
                return Long.parseLong(vb.getVariable().toString());
            }
        } catch (Exception e) {
            log.debug("Failed to get OID {}", oid, e);
            throw new SnmpTimeoutException("SNMP OID fetch failed: " + oid);
        }
        return null;
    }
    
    private Double calculatePercentage(Long part, Long total) {
        if (total == null || total == 0 || part == null) return 0.0;
        return Math.round((1.0 - part.doubleValue() / total.doubleValue()) * 100.0 * 100.0) / 100.0;
    }
}