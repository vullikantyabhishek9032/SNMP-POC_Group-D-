package com.hcl.troy.FeignClient;

import com.hcl.troy.DTO.SnmpAlert;
import com.hcl.troy.DTO.SnmpTrapDTO;
import com.hcl.troy.DTO.SystemMetrics;
import com.hcl.troy.DTO.TrapVarbindDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="producer-kafka-service",url = "${producer.kafka.service}")
public interface ProducerServiceCall {

    @PostMapping("/events/metrics")
    String publishMetrics(@RequestBody SystemMetrics event);

    @PostMapping("/events/publish/alerts")
    String publishAlert(@RequestBody SnmpAlert event);

    @PostMapping("/events/publish/snmptraps")
    String publishTrap(@RequestBody SnmpTrapDTO event);

    @PostMapping("/events/publish/trapvarbind")
    String publishVarbind(@RequestBody TrapVarbindDTO varbind);
}
