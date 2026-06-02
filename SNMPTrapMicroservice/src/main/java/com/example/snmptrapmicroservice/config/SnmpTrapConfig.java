package com.example.snmptrapmicroservice.config;

import lombok.Data;
import org.snmp4j.CommunityTarget;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.mp.SnmpConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "snmp.trap")
@Data
public class SnmpTrapConfig {
    
	 	private String host = "127.0.0.1";
	    private Integer port = 162;
	    private String community = "public";

	    @Bean
	    public CommunityTarget<Address> trapTarget() {

	        Address address =
	            GenericAddress.parse("udp:" + host + "/" + port);

	        CommunityTarget<Address> target =
	            new CommunityTarget<>();

	        target.setAddress(address);
	        target.setCommunity(new OctetString(community));
	        target.setVersion(SnmpConstants.version2c);

	        return target;
	    }
}