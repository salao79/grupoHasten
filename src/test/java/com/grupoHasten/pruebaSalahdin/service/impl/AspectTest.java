package com.grupoHasten.pruebaSalahdin.service.impl;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.grupoHasten.pruebaSalahdin.PruebaSalahdinApplication;
import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PruebaSalahdinApplication.class)
public class AspectTest {

    @Autowired
    private NaveEspacialService naveEspacialService;

    private InMemoryAppender inMemoryAppender;


    @BeforeEach
    void setUp() {
        Logger logger = (Logger) LoggerFactory.getLogger(NaveEspacialService.class);
        inMemoryAppender = new InMemoryAppender();
        inMemoryAppender.setContext(logger.getLoggerContext());
        logger.addAppender(inMemoryAppender);
        inMemoryAppender.start();
    }


    @Test
    void testAspecto() {
        NaveEspacialDTO naveEspacialDTO = new NaveEspacialDTO(-1L, "Falcon Heavy");
        naveEspacialService.save(naveEspacialDTO);

        List<ILoggingEvent> logEvents = inMemoryAppender.getEvents();
        assertEquals(1, logEvents.size());
        assertEquals("Hay una nave espacial con id -1", logEvents.get(0).getFormattedMessage());
    }
}
