package com.hcl.troy.Service;

import com.hcl.troy.DTO.SnmpAlert;
import com.hcl.troy.DTO.SnmpResponse;
import com.hcl.troy.DTO.SnmpTrapDTO;
import com.hcl.troy.DTO.SystemMetrics;
import com.hcl.troy.DTO.TrapVarbindDTO;
import com.hcl.troy.Entity.AlertEntity;
import com.hcl.troy.Entity.MetricsEntity;
import com.hcl.troy.Entity.SnmpTrapEntity;
import com.hcl.troy.Entity.TrapVarbindEntity;
import com.hcl.troy.Repo.AlertRepository;
import com.hcl.troy.Repo.MetricsRepository;
import com.hcl.troy.Repo.SnmpTrapRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private final SnmpTrapRepository repository;

    @Value("${snmp.url}")
    private String snampUrl;

    @Value("${producer.kafka.service}")
    private String producerKafka;

    @Value("${snmp.trap.url}")
    private String snmpTrapUrl;

    /* private static final String BASE_URL =
             "http://localhost:8085/api/traps";*/
    public MonitoringService(RestTemplate restTemplate, MetricsRepository metricsRepository, AlertRepository alertRepository, SnmpTrapRepository repository) {
        this.restTemplate = restTemplate;
        this.metricsRepository = metricsRepository;
        this.alertRepository = alertRepository;
        this.repository = repository;
    }


    public SnmpResponse getStatus(String hostname) {

        String url = snampUrl+"api/snmp/status/" + hostname;

        return restTemplate.getForObject(
                url,
                SnmpResponse.class);
    }

    public SnmpResponse getAlerts(String hostname) {

        String url = snampUrl+"api/snmp/alerts/" + hostname;

        return restTemplate.getForObject(
                url,
                SnmpResponse.class);
    }

    public List<SnmpResponse> getBulkMetrics(List<String> hostnames) {

        String url = snampUrl+"/api/snmp/metrics/bulk";

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
                snampUrl+"api/snmp/metrics/" + hostname,
                SnmpResponse.class);
    }

    public SnmpResponse fetchAndStoreMetrics(String hostname) {

        String url = snampUrl+"api/snmp/metrics/" + hostname;

        SnmpResponse response =
                restTemplate.getForObject(
                        url,
                        SnmpResponse.class);

        if(response == null || response.getMetrics() == null){
            log.error("No response from SNMP service");
            return response;
        }

        saveMetrics(response.getMetrics());

        if(response.getAlerts() != null &&
                !response.getAlerts().isEmpty()) {

            response.getAlerts()
                    .forEach(this::saveAlert);
        }
        return response;
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
                producerKafka+"events/metrics",
                event,
                String.class);
    }

    private void publishAlert(SnmpAlert event) {

        restTemplate.postForObject(
                producerKafka+"events/publish/alerts",
                event,
                String.class);
    }

    public void publishTraps(SnmpTrapDTO event) {

        restTemplate.postForObject(
                producerKafka+"events/publish/snmptraps",
                event,
                String.class);
    }

    public  void publishVarbind(TrapVarbindDTO varbind){
        restTemplate.postForObject(
                producerKafka+"events/publish/trapvarbind",
                varbind,
                String.class);
    }
    public void processTraps() {
        SnmpTrapDTO[] traps =
                restTemplate.getForObject(
                        snmpTrapUrl,
                        SnmpTrapDTO[].class);


        if (traps == null) {
            return;
        }

        for (SnmpTrapDTO trap : traps) {

            publishTraps(trap);

            if (trap.getVarbinds() != null) {

                for (TrapVarbindDTO varbind : trap.getVarbinds()) {

                    publishVarbind(varbind);
                }
            }
        }




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


    public void syncAllTraps() {

        SnmpTrapDTO[] traps =
                restTemplate.getForObject(
                        snmpTrapUrl,
                        SnmpTrapDTO[].class);

        saveTraps(traps);
    }

    public void syncRecentTraps(int limit) {

        SnmpTrapDTO[] traps =
                restTemplate.getForObject(
                        snmpTrapUrl + "/recent?limit=" + limit,
                        SnmpTrapDTO[].class);

        saveTraps(traps);
    }

    public void syncHostTraps(String host) {

        SnmpTrapDTO[] traps =
                restTemplate.getForObject(
                        snmpTrapUrl + "/host/" + host,
                        SnmpTrapDTO[].class);

        saveTraps(traps);
    }

    public SnmpTrapDTO[] syncSeverityTraps(String severity) {

        SnmpTrapDTO[] traps =
                restTemplate.getForObject(
                        snmpTrapUrl + "/severity/" + severity,
                        SnmpTrapDTO[].class);

        saveTraps(traps);
        return traps;
    }

    public void syncTrapById(Long trapId) {

        SnmpTrapDTO dto =
                restTemplate.getForObject(
                        snmpTrapUrl + "/" + trapId,
                        SnmpTrapDTO.class);

        if(dto == null) {
            return;
        }

        if(repository.existsById(dto.getTrapId())) {
            return;
        }

        repository.save(convertToEntity(dto));
    }

    private void saveTraps(SnmpTrapDTO[] traps) {

        if(traps == null) {
            return;
        }

        for(SnmpTrapDTO dto : traps) {

            if(repository.existsById(dto.getTrapId())) {
                continue;
            }

            repository.save(convertToEntity(dto));

            log.info("Saved Trap Id : {}", dto.getTrapId());
        }
    }



    public void syncTraps() {

        log.info("===== Starting Trap Sync =====");

        ResponseEntity<SnmpTrapDTO[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8085/api/traps",
                        SnmpTrapDTO[].class);

        SnmpTrapDTO[] traps = response.getBody();

        if (traps == null) {
            return;
        }

        for (SnmpTrapDTO dto : traps) {
            log.info("Processing Trap Id: {}", dto.getTrapId());

            if(repository.existsById(dto.getTrapId())) {
                log.info("Saved Trap Id: {}", dto.getTrapId());
                continue;

            }

            SnmpTrapEntity entity =
                    convertToEntity(dto);

            repository.save(entity);

            log.info("===== Trap Sync Completed =====");
        }
    }

    private SnmpTrapEntity convertToEntity(
            SnmpTrapDTO dto) {

        SnmpTrapEntity trap =
                new SnmpTrapEntity();

        trap.setTrapId(dto.getTrapId());
        trap.setTimestamp(dto.getTimestamp());
        trap.setHost(dto.getHost());
        trap.setCommunity(dto.getCommunity());
        trap.setTrapType(dto.getTrapType());
        trap.setVersion(dto.getVersion());
        trap.setSnmpTrapOid(dto.getSnmpTrapOid());
        trap.setSeverity(dto.getSeverity());
        trap.setMessage(dto.getMessage());
        trap.setProcessed(dto.getProcessed());

        if(dto.getVarbinds() != null) {

            List<TrapVarbindEntity> varbinds =
                    dto.getVarbinds()
                            .stream()
                            .map(v -> {

                                TrapVarbindEntity entity =
                                        new TrapVarbindEntity();

                                entity.setOid(v.getOid());
                                entity.setType(v.getType());
                                entity.setValue(v.getValue());
                                entity.setTrap(trap);

                                return entity;

                            }).toList();

            trap.setVarbinds(varbinds);
        }

        return trap;
    }
}