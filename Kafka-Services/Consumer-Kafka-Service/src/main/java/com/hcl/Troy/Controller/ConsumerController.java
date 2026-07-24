package com.hcl.Troy.Controller;

import com.hcl.Troy.DTO.*;
import com.hcl.Troy.Service.AlarmConsumerService;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consumer")
@CrossOrigin(origins ={"http://localhost:3000","http://localhost:30080"})
public class ConsumerController {

    private final AlarmConsumerService consumerService;
    @Autowired
    private StreamsBuilderFactoryBean factoryBean;
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

        KafkaStreams streams=factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, RecommendationDTO> store =
                streams.store(
                        StoreQueryParameters.fromNameAndType(
                                "recommendation-store",
                                QueryableStoreTypes.keyValueStore()));

        List<RecommendationDTO> list = new ArrayList<>();

        KeyValueIterator<String, RecommendationDTO> iterator = store.all();

        while(iterator.hasNext()){
            list.add(iterator.next().value);
        }

        iterator.close();

        return list;

    }
}