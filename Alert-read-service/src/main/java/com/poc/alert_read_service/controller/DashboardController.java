package com.poc.alert_read_service.controller;

import com.poc.alert_read_service.dto.DashboardSummary;
import com.poc.alert_read_service.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/dashboard/summary")
    public DashboardSummary getDashboardSummary() {
        return dashboardService.getDashboardSummary();
    }
}