package com.hcl.troy.FeignClient;


import com.hcl.troy.DTO.SnmpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name ="SnmpMicroservice-1",url = "${snmp.url}")
public interface SnmpServiceCall {

    @GetMapping("api/snmp/status/{hostname}")
    SnmpResponse getStatus(@PathVariable("hostname") String hostname);

    @GetMapping("api/snmp/alerts/{hostname}")
    SnmpResponse getAlerts(@PathVariable("hostname") String hostname);


    @PostMapping("/api/snmp/metrics/bulk")
    List<SnmpResponse> getBulkMetrics(@RequestBody List<String> hostnames);

    @GetMapping("api/snmp/metrics/{hostname}")
    SnmpResponse fetchFromSnmp(@PathVariable("hostname") String hostname);

    @GetMapping("api/snmp/metrics/{hostname}")
    SnmpResponse fetchAndStoreMetrics(@PathVariable("hostname") String hostname);
}
