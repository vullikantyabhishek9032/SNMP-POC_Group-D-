package com.hcl.troy.Controller;

import com.hcl.troy.DTO.SnmpResponse;
import com.hcl.troy.Service.MonitoringService;
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
//@RequiredArgsConstructor
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
    public String syncRecentTraps(
            @RequestParam(defaultValue = "10")
            int limit) {

        service.syncRecentTraps(limit);

        return "Recent Traps Synced";
    }

    @GetMapping("/traps/host/{host}")
    public String syncHostTraps(
            @PathVariable String host) {

        service.syncHostTraps(host);

        return "Host Traps Synced";
    }

    @GetMapping("/traps/severity/{severity}")
    public String syncSeverityTraps(
            @PathVariable String severity) {

        service.syncSeverityTraps(severity);

        return "Severity Traps Synced";
    }

    @GetMapping("/traps/{trapId}")
    public String syncTrapById(
            @PathVariable Long trapId) {

        service.syncTrapById(trapId);

        return "Trap Synced";
    }

    @GetMapping("/collect/{hostname}")
    public String collectMetrics(
            @PathVariable String hostname) {

        service.fetchAndStoreMetrics(hostname);
        service.processMetrics(service.fetchFromSnmp(hostname));

        return "Metrics Saved Successfully";
    }

    @GetMapping("/status/{hostname}")
    public SnmpResponse getStatus(
            @PathVariable String hostname) {

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