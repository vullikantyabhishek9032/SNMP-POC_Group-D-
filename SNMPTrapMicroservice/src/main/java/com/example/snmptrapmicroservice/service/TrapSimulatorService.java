package com.example.snmptrapmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.snmp4j.*;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrapSimulatorService {
    
    private final TrapProcessingService trapProcessingService;
    
    @Value("${simulator.enabled:true}")
    private boolean simulatorEnabled;
    
    @Value("${snmp.trap.port}")
    private int trapPort;
    
    private static final List<String> TEST_TRAP_OIDS = List.of(
        ".1.3.6.1.4.1.9.9.48.0.1",      // Memory threshold
        ".1.3.6.1.4.1.9.9.109.0.1",     // CPU threshold
        ".1.3.6.1.2.1.1.8.0",           // System reboot
        ".1.3.6.1.4.1.9.9.13.1.3.1.3",  // Temperature
        ".1.3.6.1.2.1.2.2.1.8.1"        // Link down
    );
    
    private static final List<String> TEST_HOSTS = List.of(
        "server01", "server02", "router01", "switch01", "firewall01"
    );
    
    /**
     * Send test traps periodically for testing
     */
    @Scheduled(fixedRateString = "${simulator.trap.interval:30000}")
    public void sendTestTraps() {
        if (!simulatorEnabled) return;
        
        try {
            sendTrap();
            log.debug("Test trap sent successfully");
        } catch (Exception e) {
            log.error("Failed to send test trap", e);
        }
    }
    
    public void sendTrap() throws IOException {
        TransportMapping transport = new DefaultUdpTransportMapping();
        transport.listen();
        
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(GenericAddress.parse("udp:localhost/" + trapPort));
        target.setVersion(SnmpConstants.version2c);
        target.setTimeout(5000);
        target.setRetries(2);
        
        PDU pdu = new PDU();
        pdu.setType(PDU.NOTIFICATION);
        
        // Random trap
        String trapOid = TEST_TRAP_OIDS.get((int) (Math.random() * TEST_TRAP_OIDS.size()));
        String host = TEST_HOSTS.get((int) (Math.random() * TEST_HOSTS.size()));
        
        // Add required varbinds
        pdu.add(new VariableBinding(SnmpConstants.sysUpTime, 
            new OctetString(System.currentTimeMillis() + "")));
        pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, new OID(trapOid)));
        pdu.add(new VariableBinding(new OID(trapOid), new OctetString("Test trap from simulator")));
        
        Snmp snmp = new Snmp(transport);
        snmp.send(pdu, target);
        snmp.close();
        transport.close();
        
        log.info("Test trap sent: OID={} from {}", trapOid, host);
    }
}