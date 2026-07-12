package com.hcl.troy.Controller;

import com.hcl.troy.DTO.SnmpResponse;
import com.hcl.troy.DTO.SnmpTrapDTO;
import com.hcl.troy.Service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private final MonitoringService service;

    public MonitoringController(MonitoringService service) {
        this.service = service;
    }

    @GetMapping("/traps")
    public String syncTraps() {

        service.syncAllTraps();
        service.processTraps();

        return "All Traps Synced";
    }

    @GetMapping("/traps/recent")
    public SnmpTrapDTO[] syncRecentTraps(@RequestParam(defaultValue = "10") int limit) {

        return service.syncRecentTraps(limit);
    }

    @GetMapping("/traps/host/{host}")
    public SnmpTrapDTO[] syncHostTraps(@PathVariable String host) {

        return  service.syncHostTraps(host);
    }

    @GetMapping("/traps/severity/{severity}")
    public ResponseEntity<SnmpTrapDTO[]> syncSeverityTraps(
            @PathVariable String severity) {
        return ResponseEntity.ok(service.syncSeverityTraps(severity));
    }

    @GetMapping("/traps/{trapId}")
    public ResponseEntity<SnmpTrapDTO> syncTrapById(@PathVariable Long trapId) {

        return ResponseEntity.ok(service.syncTrapById(trapId));
    }

    @GetMapping("/collect/{hostname}")
    public ResponseEntity<SnmpResponse> collectMetrics(
            @PathVariable String hostname) {

        SnmpResponse response=service.fetchAndStoreMetrics(hostname);
        service.processMetrics(service.fetchFromSnmp(hostname));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{hostname}")
    public SnmpResponse getStatus(@PathVariable String hostname) {
        System.out.println(hostname);
        return service.getStatus(hostname);
    }

    @GetMapping("/alerts/{hostname}")
    public SnmpResponse getAlerts(
            @PathVariable String hostname) {

        return service.getAlerts(hostname);
    }

    @PostMapping("/bulk")
    public List<SnmpResponse> getBulkMetrics(
            @RequestBody List<String> hostnames) {

        return service.getBulkMetrics(hostnames);
    }

}