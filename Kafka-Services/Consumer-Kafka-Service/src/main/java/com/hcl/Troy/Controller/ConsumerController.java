package com.hcl.Troy.Controller;

import com.hcl.Troy.DTO.AlarmEvent;
import com.hcl.Troy.Service.AlarmConsumerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private final AlarmConsumerService consumerService;

    public ConsumerController(
            AlarmConsumerService consumerService) {

        this.consumerService = consumerService;
    }

    @GetMapping("/events")
    public List<AlarmEvent> getEvents() {

        return consumerService.getAllEvents();
    }
}