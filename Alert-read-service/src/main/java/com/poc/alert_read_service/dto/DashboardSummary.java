package com.poc.alert_read_service.dto;

public class DashboardSummary {
    private long totalAlerts;
    private long criticalAlerts;
    private long highCpuDevices;
    private long highMemoryDevices;

    public long getTotalAlerts() {
        return totalAlerts;
    }

    public void setTotalAlerts(long totalAlerts) {
        this.totalAlerts = totalAlerts;
    }

    public long getCriticalAlerts() {
        return criticalAlerts;
    }

    public void setCriticalAlerts(long criticalAlerts) {
        this.criticalAlerts = criticalAlerts;
    }

    public long getHighCpuDevices() {
        return highCpuDevices;
    }

    public void setHighCpuDevices(long highCpuDevices) {
        this.highCpuDevices = highCpuDevices;
    }

    public long getHighMemoryDevices() {
        return highMemoryDevices;
    }

    public void setHighMemoryDevices(long highMemoryDevices) {
        this.highMemoryDevices = highMemoryDevices;
    }

}
