package com.grupoHasten.pruebaSalahdin.service.interfaces.broker;

import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public interface IBrokerService {
    void sendNaveEspacialToBroker(NaveEspacial nave);
}
