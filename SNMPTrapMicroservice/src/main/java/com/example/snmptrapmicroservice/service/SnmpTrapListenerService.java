package com.example.snmptrapmicroservice.service;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.snmptrapmicroservice.model.Trap;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnmpTrapListenerService {
    
    private final TrapProcessingService trapProcessingService;
    
    @Value("${snmp.trap.port}")
    private int trapPort;
    
    @Value("${snmp.trap.enabled}")
    private boolean trapEnabled;
    
   
    
    private Snmp snmp;
    private boolean running = false;
    
    /**
     * Start SNMP Trap listener on application startup [web:11][web:17]
     */
    @EventListener(ApplicationReadyEvent.class)
    public void startTrapListener() {
        if (!trapEnabled) {
            log.info("SNMP Trap listener is disabled");
            return;
        }
        
        try {
            TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping(new UdpAddress("0.0.0.0/" + trapPort));
            snmp = new Snmp(transport);
            
            // Add command responder to receive traps
            snmp.addCommandResponder(new TrapReceiver());
            
            transport.listen();
            running = true;
            
            log.info("SNMP Trap Listener started on UDP port {}", trapPort);
            log.info("Listening for SNMP traps with community: public");
            
        } catch (IOException e) {
            log.error("Failed to start SNMP Trap listener on port {}", trapPort, e);
        }
    }
    
    /**
     * Inner class implementing CommandResponder to receive traps
     */
    class TrapReceiver implements CommandResponder {
        
        @SuppressWarnings("rawtypes")
		@Override
        public void processPdu(CommandResponderEvent event) {
            try {
                PDU pdu = event.getPDU();
                if (pdu == null) return;
                
                log.debug("Received SNMP Trap PDU");
                
                // Extract trap details
                String host = event.getPDU().getErrorStatus() == 0 
                    ? event.getPeerAddress().toString() 
                    : "unknown";
                
                String community = event.getSecurityName() != null 
                    ? event.getSecurityName().toString() 
                    : "public";
                
                // Get trap OID
                String trapOid = null;
                @SuppressWarnings("unchecked")
                Vector<VariableBinding> bindings = new Vector(pdu.getVariableBindings());
                
                for (int i = 0; i < bindings.size(); i++) {
                    VariableBinding vb = bindings.get(i);
                    if (vb.getOid().equals(SnmpConstants.snmpTrapOID)) {
                        trapOid = vb.getVariable().toString();
                        break;
                    }
                }
                
                // Process trap
                trapProcessingService.processTrap(
                    host,
                    community,
                    pdu.getType(),
                    trapOid,
                    bindings,
                    "v2c"
                );
                
                log.info("Trap processed from {}: OID={}", host, trapOid);
                
            } catch (Exception e) {
                log.error("Error processing SNMP trap", e);
            }
        }
       
        public PDU onResponse(CommandResponderEvent event) {
            return null;
        }
    }
    
    @PreDestroy
    public void stopTrapListener() {
        if (snmp != null) {
            try {
                snmp.close();
                running = false;
                log.info("SNMP Trap listener stopped");
            } catch (Exception e) {
                log.error("Error stopping SNMP trap listener", e);
            }
        }
    }
    
    public boolean isRunning() {
        return running;
    }
}