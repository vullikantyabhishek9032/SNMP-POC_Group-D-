package com.hcl.Troy.Controller;

import com.hcl.Troy.DTO.AlarmEvent;
import com.hcl.Troy.DTO.AlertEvent;
import com.hcl.Troy.DTO.MonitoringEvent;
import com.hcl.Troy.DTO.SnmpTrapDTO;
import com.hcl.Troy.DTO.TrapVarbindDTO;
import com.hcl.Troy.Service.AlarmProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class AlarmController {

    @Autowired
    private AlarmProducerService producerService;

    @PostMapping("/metrics")
    public String publishAlarm(@RequestBody MonitoringEvent event) {

        producerService.publishMetrics(event);
        return "Alarm Published";
    }

    @PostMapping("/publish/alerts")
    public String publishCriticalAlarm(
            @RequestBody AlertEvent event) {

        producerService.publishAlert(event);

        return "Critical Alarm Published";
    }

    @PostMapping("/publish/snmptraps")
    public String publishTrap(
            @RequestBody SnmpTrapDTO event) {

        producerService.publishSnmoTrap(event);

        return "Traps  Published";
    }
    @PostMapping("/publish/trapvarbind")
    public String publishVarbindTrap(
            @RequestBody TrapVarbindDTO event) {

        producerService.publishTrapVarTrap(event);

        return "Traps  Published";
    }


}