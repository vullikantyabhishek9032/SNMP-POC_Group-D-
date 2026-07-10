package com.hcl.Troy.Controller;

import com.hcl.Troy.DTO.*;
import com.hcl.Troy.Service.AlarmProducerService;
import com.hcl.Troy.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class AlarmController {

    @Autowired
    private AlarmProducerService producerService;
    @Autowired
    private EmailService emailService;

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

    @PostMapping("/streamData")
    public String sendUsage(@RequestBody CustomerUsage payload) {

        producerService.sendUsage( payload);

        return "Message sent to usage-topic";
    }




}