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
import com.hcl.troy.FeignClient.ProducerServiceCall;
import com.hcl.troy.FeignClient.SnmpServiceCall;
import com.hcl.troy.FeignClient.SnmpTrapServiceCall;
import com.hcl.troy.Repo.AlertRepository;
import com.hcl.troy.Repo.MetricsRepository;
import com.hcl.troy.Repo.SnmpTrapRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MonitoringService {

    private final MetricsRepository metricsRepository;
    private final AlertRepository alertRepository;

    private final SnmpTrapRepository repository;

    private final SnmpServiceCall snmpServiceCall;

    private final SnmpTrapServiceCall snmpTrapServiceCall;

    private final ProducerServiceCall producerServiceCall;

    public MonitoringService(MetricsRepository metricsRepository, AlertRepository alertRepository, SnmpTrapRepository repository,SnmpServiceCall snmpServiceCall,SnmpTrapServiceCall snmpTrapServiceCall,ProducerServiceCall producerServiceCall) {
        this.metricsRepository = metricsRepository;
        this.alertRepository = alertRepository;
        this.repository = repository;
        this.snmpServiceCall=snmpServiceCall;
        this.snmpTrapServiceCall=snmpTrapServiceCall;
        this.producerServiceCall=producerServiceCall;
    }


    public SnmpResponse getStatus(String hostname) {
       return snmpServiceCall.getStatus(hostname);
    }

    public SnmpResponse getAlerts(String hostname) {
        return snmpServiceCall.getAlerts(hostname);
    }

    public List<SnmpResponse> getBulkMetrics(List<String> hostnames) {

        return snmpServiceCall.getBulkMetrics(hostnames);
    }

    public SnmpResponse fetchFromSnmp(String hostname) {

        return snmpServiceCall.fetchFromSnmp(hostname);
    }

    public SnmpResponse fetchAndStoreMetrics(String hostname) {
        SnmpResponse response =snmpServiceCall.fetchAndStoreMetrics(hostname);
        if(response == null || response.getMetrics() == null){
            log.error("No response from SNMP service");
            return response;
        }

        saveMetrics(response.getMetrics());

        if(response.getAlerts() != null && !response.getAlerts().isEmpty()) {

            response.getAlerts().forEach(this::saveAlert);
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
        producerServiceCall.publishMetrics(event);
    }

    private void publishAlert(SnmpAlert event) {

        producerServiceCall.publishAlert(event);
    }

    public void publishTraps(SnmpTrapDTO event) {
        producerServiceCall.publishTrap(event);
    }

    public  void publishVarbind(TrapVarbindDTO varbind){
        producerServiceCall.publishVarbind(varbind);
    }
    public void processTraps() {
        SnmpTrapDTO[] traps =snmpTrapServiceCall.processTraps();


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


    public void processMetrics(SnmpResponse response) {

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

        SnmpTrapDTO[] traps =snmpTrapServiceCall.syncAllTraps();

        saveTraps(traps);
    }

    public SnmpTrapDTO[] syncRecentTraps(int limit) {

        SnmpTrapDTO[] traps =snmpTrapServiceCall.syncRecentTraps(limit);

        saveTraps(traps);
        return traps;
    }

    public SnmpTrapDTO[] syncHostTraps(String host) {

        SnmpTrapDTO[] traps = snmpTrapServiceCall.syncHostTraps(host);

        saveTraps(traps);
        return traps;
    }

    public SnmpTrapDTO[] syncSeverityTraps(String severity) {

        SnmpTrapDTO[] traps =snmpTrapServiceCall.syncSeverityTraps(severity);

        saveTraps(traps);
        return traps;
    }

    public SnmpTrapDTO syncTrapById(Long trapId) {

        SnmpTrapDTO dto =snmpTrapServiceCall.syncTrapById(trapId);

        if(dto == null) {
            return dto;
        }

        if(repository.existsById(dto.getTrapId())) {
            return dto;
        }

        repository.save(convertToEntity(dto));
        return dto;
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

        ResponseEntity<SnmpTrapDTO[]> response =snmpTrapServiceCall.syncTraps();
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

    private SnmpTrapEntity convertToEntity(SnmpTrapDTO dto) {

        SnmpTrapEntity trap = new SnmpTrapEntity();

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
                                TrapVarbindEntity entity = new TrapVarbindEntity();

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