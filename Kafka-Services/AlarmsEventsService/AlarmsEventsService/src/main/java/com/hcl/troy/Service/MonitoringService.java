package com.hcl.troy.Service;

import com.hcl.troy.DTO.SnmpAlert;
import com.hcl.troy.DTO.SnmpResponse;
import com.hcl.troy.DTO.SystemMetrics;
import com.hcl.troy.Entity.AlertEntity;
import com.hcl.troy.Entity.MetricsEntity;
import com.hcl.troy.Repo.AlertRepository;
import com.hcl.troy.Repo.MetricsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class MonitoringService {

    private final RestTemplate restTemplate;
    private final MetricsRepository metricsRepository;
    private final AlertRepository alertRepository;

    public MonitoringService(RestTemplate restTemplate, MetricsRepository metricsRepository, AlertRepository alertRepository) {
        this.restTemplate = restTemplate;
        this.metricsRepository = metricsRepository;
        this.alertRepository = alertRepository;
    }


    public SnmpResponse getStatus(String hostname) {

        String url =
                "http://localhost:8080/api/snmp/status/" + hostname;

        return restTemplate.getForObject(
                url,
                SnmpResponse.class);
    }

    public SnmpResponse getAlerts(String hostname) {

        String url =
                "http://localhost:8080/api/snmp/alerts/" + hostname;

        return restTemplate.getForObject(
                url,
                SnmpResponse.class);
    }

    public List<SnmpResponse> getBulkMetrics(List<String> hostnames) {

        String url = "http://localhost:8080/api/snmp/metrics/bulk";

        HttpEntity<List<String>> request = new HttpEntity<>(hostnames);

        ResponseEntity<List<SnmpResponse>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        new ParameterizedTypeReference<List<SnmpResponse>>() {
                        });

        return response.getBody();
    }

    public SnmpResponse fetchFromSnmp(String hostname) {

        return restTemplate.getForObject(
                "http://localhost:8080/api/snmp/metrics/" + hostname,
                SnmpResponse.class);
    }

    public void fetchAndStoreMetrics(String hostname) {

        String url = "http://localhost:8080/api/snmp/metrics/" + hostname;

        SnmpResponse response =
                restTemplate.getForObject(
                        url,
                        SnmpResponse.class);

        if(response == null || response.getMetrics() == null){
            log.error("No response from SNMP service");
            return;
        }

        saveMetrics(response.getMetrics());

        if(response.getAlerts() != null &&
                !response.getAlerts().isEmpty()) {

            response.getAlerts()
                    .forEach(this::saveAlert);
        }
    }

    private void saveMetrics(SystemMetrics metrics) {

        MetricsEntity entity = new MetricsEntity();

        entity.setHostname(metrics.getHostname());
        entity.setCpuUsage(metrics.getCpuUsage());
        entity.setMemoryUsage(metrics.getMemoryUsage());
        entity.setMemoryTotal(metrics.getMemoryTotal());
        entity.setMemoryUsed(metrics.getMemoryUsed());
        entity.setMemoryAvailable(metrics.getMemoryAvailable());
        entity.setDiskUsage(metrics.getDiskUsage());
        entity.setUptime(metrics.getUptime());
        entity.setMetricTimestamp(metrics.getTimestamp());

        metricsRepository.save(entity);

        log.info("Metrics saved for {}", metrics.getHostname());
    }

    private void saveAlert(SnmpAlert alert) {

        AlertEntity entity = new AlertEntity();

        entity.setAlertId(alert.getAlertId());
        entity.setHostname(alert.getHostname());
        entity.setAlertType(alert.getAlertType());
        entity.setSeverity(alert.getSeverity());
        entity.setMessage(alert.getMessage());
        entity.setCurrentValue(alert.getCurrentValue());
        entity.setThresholdValue(alert.getThreshold());
        entity.setAlertTimestamp(alert.getTimestamp());

        alertRepository.save(entity);

        log.info("Alert saved for {}", alert.getHostname());
    }


    private void publishMetrics(SystemMetrics event) {

        restTemplate.postForObject(
                "http://localhost:8081/events/metrics",
                event,
                String.class);
    }

    private void publishAlert(SnmpAlert event) {

        restTemplate.postForObject(
                "http://localhost:8081/events/publish/alerts",
                event,
                String.class);
    }

    public void processMetrics(
            SnmpResponse response) {

        saveMetrics(response.getMetrics());

        publishMetrics(response.getMetrics());

        if(response.getAlerts() != null &&
                !response.getAlerts().isEmpty()) {

            response.getAlerts()
                    .forEach(alert -> {

                        saveAlert(alert);

                        publishAlert(alert);
                    });
        }
    }
}