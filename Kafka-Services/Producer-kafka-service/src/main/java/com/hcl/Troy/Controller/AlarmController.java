package com.hcl.Troy.Controller;

import com.hcl.Troy.DTO.AlarmEvent;
import com.hcl.Troy.Service.AlarmProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarms")
public class AlarmController {

    @Autowired
    private AlarmProducerService producerService;

    @PostMapping
    public String publishAlarm(@RequestBody AlarmEvent event) {

        producerService.sendAlarm(event);
        return "Alarm Published";
    }

    @PostMapping("/publish/critical")
    public String publishCriticalAlarm(
            @RequestBody AlarmEvent event) {

        producerService.publishCritical(event);

        return "Critical Alarm Published";
    }
}