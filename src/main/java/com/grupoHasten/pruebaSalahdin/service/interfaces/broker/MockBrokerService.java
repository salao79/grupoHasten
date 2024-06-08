package com.grupoHasten.pruebaSalahdin.service.interfaces.broker;

import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class MockBrokerService implements IBrokerService{
    @Override
    public void sendNaveEspacialToBroker(NaveEspacial nave) {

    }
}
