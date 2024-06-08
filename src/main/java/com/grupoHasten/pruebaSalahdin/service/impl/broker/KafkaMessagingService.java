package com.grupoHasten.pruebaSalahdin.service.impl.broker;

import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import com.grupoHasten.pruebaSalahdin.service.interfaces.broker.IBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
@ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaMessagingService implements IBrokerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaMessagingService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendNaveEspacialToBroker(NaveEspacial nave) {
        kafkaTemplate.send("nave-topic", nave);
    }
}
