package com.grupoHasten.pruebaSalahdin.service.impl;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.grupoHasten.pruebaSalahdin.PruebaSalahdinApplication;
import com.grupoHasten.pruebaSalahdin.exception.BadRequestException;
import com.grupoHasten.pruebaSalahdin.exception.ResourceNotFoundException;
import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import com.grupoHasten.pruebaSalahdin.repository.INaveEspacialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PruebaSalahdinApplication.class)
public class IntegrationTests {

    @Autowired
    private NaveEspacialService naveEspacialService;

    @Autowired
    private INaveEspacialRepository naveEspacialRepository;

    private InMemoryAppender inMemoryAppender;

    @BeforeEach
    void setUp() {
        naveEspacialRepository.deleteAll();

        NaveEspacial naveEspacial1 = new NaveEspacial(null, "Falcon 1");
        NaveEspacial naveEspacial2 = new NaveEspacial(null, "Falcon 9");
        naveEspacialRepository.save(naveEspacial1);
        naveEspacialRepository.save(naveEspacial2);

        Logger logger = (Logger) LoggerFactory.getLogger(NaveEspacialService.class);
        inMemoryAppender = new InMemoryAppender();
        inMemoryAppender.setContext(logger.getLoggerContext());
        logger.addAppender(inMemoryAppender);
        inMemoryAppender.start();
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<NaveEspacialDTO> result = naveEspacialService.findAll(pageable);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testFindById() {
        NaveEspacial savedNaveEspacial = naveEspacialRepository.findByNameContaining("Falcon 1", PageRequest.of(0, 1)).getContent().get(0);
        NaveEspacialDTO result = naveEspacialService.findById(savedNaveEspacial.getId());

        assertEquals("Falcon 1", result.getName());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            naveEspacialService.findById(999L);
        });
    }

    @Test
    void testFindByNameContaining() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<NaveEspacialDTO> result = naveEspacialService.findByNameContaining("Falcon", pageable);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testSave() {
        NaveEspacialDTO naveEspacialDTO = new NaveEspacialDTO(null, "Falcon Heavy");
        NaveEspacialDTO result = naveEspacialService.save(naveEspacialDTO);

        assertEquals("Falcon Heavy", result.getName());
        assertEquals(3, naveEspacialRepository.count());
    }

    @Test
    void testSaveInvalidData() {
        NaveEspacialDTO naveEspacialDTO = null;

        assertThrows(BadRequestException.class, () -> {
            naveEspacialService.save(naveEspacialDTO);
        });

        assertEquals(2, naveEspacialRepository.count());
    }

    @Test
    void testAspecto() {
        NaveEspacialDTO naveEspacialDTO = new NaveEspacialDTO(-1L, "Falcon Heavy");
        naveEspacialService.save(naveEspacialDTO);

        List<ILoggingEvent> logEvents = inMemoryAppender.getEvents();
        assertEquals(1, logEvents.size());
        assertEquals("Hay una nave espacial con id -1", logEvents.get(0).getFormattedMessage());
    }

    @Test
    void testUpdate() {
        NaveEspacial savedNaveEspacial = naveEspacialRepository.findByNameContaining("Falcon 1", PageRequest.of(0, 1)).getContent().get(0);
        NaveEspacialDTO naveEspacialDTO = new NaveEspacialDTO(savedNaveEspacial.getId(), "Falcon 1 Updated");
        NaveEspacialDTO result = naveEspacialService.update(naveEspacialDTO);

        assertEquals("Falcon 1 Updated", result.getName());
    }

    @Test
    void testDeleteById() {
        NaveEspacial savedNaveEspacial = naveEspacialRepository.findByNameContaining("Falcon 1", PageRequest.of(0, 1)).getContent().get(0);
        naveEspacialService.deleteById(savedNaveEspacial.getId());

        assertEquals(1, naveEspacialRepository.count());
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            naveEspacialService.deleteById(999L);
        });

        assertEquals(2, naveEspacialRepository.count());
    }
}
