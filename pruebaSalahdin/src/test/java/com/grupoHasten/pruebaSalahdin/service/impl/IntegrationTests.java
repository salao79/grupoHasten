package com.grupoHasten.pruebaSalahdin.service.impl;

import com.grupoHasten.pruebaSalahdin.PruebaSalahdinApplication;
import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import com.grupoHasten.pruebaSalahdin.repository.INaveEspacialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PruebaSalahdinApplication.class)
@ComponentScan(basePackages = "com.grupoHasten.pruebaSalahdin")
public class IntegrationTests {

    @Autowired
    private NaveEspacialService naveEspacialService;

    @Autowired
    private INaveEspacialRepository naveEspacialRepository;

    @BeforeEach
    void setUp() {
        naveEspacialRepository.deleteAll();

        NaveEspacial naveEspacial1 = new NaveEspacial(null, "Falcon 1");
        NaveEspacial naveEspacial2 = new NaveEspacial(null, "Falcon 9");
        naveEspacialRepository.save(naveEspacial1);
        naveEspacialRepository.save(naveEspacial2);
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
}
