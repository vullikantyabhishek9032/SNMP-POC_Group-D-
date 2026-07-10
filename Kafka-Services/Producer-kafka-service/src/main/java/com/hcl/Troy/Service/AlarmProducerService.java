package com.hcl.Troy.Service;

import com.fasterxml.jackson.databind.Module;
import com.hcl.Troy.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AlarmProducerService {

    private final KafkaTemplate<String, MonitoringEvent> metricsKafkaTemplate;

    private final KafkaTemplate<String, AlertEvent> alertKafkaTemplate;

    private final KafkaTemplate<String, SnmpTrapDTO> kafkaTrapTemplate;

    private final KafkaTemplate<String, TrapVarbindDTO> kafkaVarbindTrapTemplate;

    private final  KafkaTemplate<String, CustomerUsage> kafkaTemplate;

    public AlarmProducerService(
            KafkaTemplate<String, MonitoringEvent> metricsKafkaTemplate, KafkaTemplate<String, AlertEvent> alertKafkaTemplate, KafkaTemplate<String, SnmpTrapDTO> kafkaTrapTemplate, KafkaTemplate<String, TrapVarbindDTO> kafkaVarbindTrapTemplate, @Qualifier("kafkaCustomerTemplate") KafkaTemplate<String, CustomerUsage> kafkaTemplate) {

        this.metricsKafkaTemplate = metricsKafkaTemplate;
        this.alertKafkaTemplate = alertKafkaTemplate;
        this.kafkaTrapTemplate = kafkaTrapTemplate;
        this.kafkaVarbindTrapTemplate = kafkaVarbindTrapTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }



    public void publishMetrics(
            MonitoringEvent event) {

        metricsKafkaTemplate.send(
                "metrics-topic",
                event);
    }

    public void publishAlert(
            AlertEvent event) {

        alertKafkaTemplate.send(
                "alerts-topic",
                event);
    }

    public void publishSnmoTrap(SnmpTrapDTO dto) {

        kafkaTrapTemplate.send(
                "snm-trap-topic",
                String.valueOf(dto.getTrapId()),
                dto);
    }


    public void publishTrapVarTrap(TrapVarbindDTO dto) {

        kafkaVarbindTrapTemplate.send(
                "trap-varbinds-topic",
                String.valueOf(dto.getOid()),
                dto);
    }

    public String sendUsage(@RequestBody CustomerUsage payload) {
        kafkaTemplate.send("usage-topic", payload);

        return "Message sent to usage-topic";
    }


}