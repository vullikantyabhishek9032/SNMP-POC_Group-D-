package com.hcl.Troy.Controller;

import com.hcl.Troy.DTO.*;
import com.hcl.Troy.Service.AlarmConsumerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
@CrossOrigin(origins ="http://localhost:3000")
public class ConsumerController {

    private final AlarmConsumerService consumerService;

    public ConsumerController(
            AlarmConsumerService consumerService) {

        this.consumerService = consumerService;
    }

    @GetMapping("/metrics/events")
    public List<MonitoringEvent> getEvents() {

        return consumerService.getAllEvents();
    }

    @GetMapping("/alerts/events")
    public List<AlertEvent> getAlertEvents() {

        return consumerService.getAllAlertEvents();
    }


    @GetMapping("/recommendations")
    public List<RecommendationDTO> getRecommendations() {

        return consumerService.getRecommendations();
    }





}