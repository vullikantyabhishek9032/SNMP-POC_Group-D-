package com.example.snmpmicroservice.config;

import lombok.Data;
import org.snmp4j.CommunityTarget;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.mp.SnmpConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "snmp")
@Data
public class SnmpConfig {
    private String managerHost = "localhost";
    private Integer managerPort = 161;
    private String communityPublic = "public";
    private Integer timeout = 3000;
    private Integer retries = 3;
    private Boolean simulatorEnabled = true;
    
    @SuppressWarnings("rawtypes")
	@Bean
    public CommunityTarget snmpTarget() {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(communityPublic));
        target.setAddress(GenericAddress.parse("udp:" + managerHost + "/" + managerPort));
        target.setVersion(SnmpConstants.version2c);
        target.setTimeout(timeout);
        target.setRetries(retries);
        return target;
    }
}