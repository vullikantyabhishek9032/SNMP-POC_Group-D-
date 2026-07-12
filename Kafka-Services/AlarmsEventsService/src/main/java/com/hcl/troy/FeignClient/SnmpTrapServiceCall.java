package com.hcl.troy.FeignClient;

import com.hcl.troy.DTO.SnmpTrapDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name ="SNMPTrapMicroservice",url = "${snmp.trap.url}")
public interface SnmpTrapServiceCall {

    @GetMapping
    SnmpTrapDTO[] syncAllTraps();


    @GetMapping("/recent")
    SnmpTrapDTO[] syncRecentTraps(@RequestParam int limit);

    @GetMapping("/host/{host}")
    SnmpTrapDTO[] syncHostTraps(@PathVariable("host") String host);



    @GetMapping("/severity/{severity}")
    SnmpTrapDTO[] syncSeverityTraps(@PathVariable("severity") String severity);



    @GetMapping("/{trapId}")
    SnmpTrapDTO syncTrapById(@PathVariable("trapId")Long trapId);


    @GetMapping
    ResponseEntity<SnmpTrapDTO[]> syncTraps();

    @GetMapping
    SnmpTrapDTO[] processTraps();


}
