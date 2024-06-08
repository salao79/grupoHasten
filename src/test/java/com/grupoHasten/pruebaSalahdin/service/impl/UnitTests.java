package com.grupoHasten.pruebaSalahdin.service.impl;

import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import com.grupoHasten.pruebaSalahdin.model.dto.exception.BadRequestException;
import com.grupoHasten.pruebaSalahdin.model.dto.exception.ResourceNotFoundException;
import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import com.grupoHasten.pruebaSalahdin.repository.INaveEspacialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTests {

    @Mock
    private INaveEspacialRepository naveEspacialRepository;

    @InjectMocks
    private NaveEspacialService naveEspacialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        NaveEspacial naveEspacial = new NaveEspacial(1L, "Falcon");
        Page<NaveEspacial> page = new PageImpl<>(Collections.singletonList(naveEspacial));
        when(naveEspacialRepository.findAll(pageable)).thenReturn(page);

        Page<NaveEspacialDTO> result = naveEspacialService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(naveEspacialRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFindById() {
        NaveEspacial naveEspacial = new NaveEspacial(1L, "Falcon");
        when(naveEspacialRepository.findById(1L)).thenReturn(Optional.of(naveEspacial));

        NaveEspacialDTO result = naveEspacialService.findById(1L);

        assertEquals("Falcon", result.getName());
        verify(naveEspacialRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(naveEspacialRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> naveEspacialService.findById(1L));

        verify(naveEspacialRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByNameContaining() {
        Pageable pageable = PageRequest.of(0, 10);
        NaveEspacial naveEspacial = new NaveEspacial(1L, "Falcon");
        Page<NaveEspacial> page = new PageImpl<>(Collections.singletonList(naveEspacial));
        when(naveEspacialRepository.findByNameContaining("Falcon", pageable)).thenReturn(page);

        Page<NaveEspacialDTO> result = naveEspacialService.findByNameContaining("Falcon", pageable);

        assertEquals(1, result.getTotalElements());
        verify(naveEspacialRepository, times(1)).findByNameContaining("Falcon", pageable);
    }

    @Test
    void testSave() {
        NaveEspacial naveEspacial = new NaveEspacial(1L, "Falcon");
        NaveEspacialDTO naveEspacialDTO = new NaveEspacialDTO(1L, "Falcon");
        when(naveEspacialRepository.save(any(NaveEspacial.class))).thenReturn(naveEspacial);

        NaveEspacialDTO result = naveEspacialService.save(naveEspacialDTO);

        assertEquals("Falcon", result.getName());
        verify(naveEspacialRepository, times(1)).save(any(NaveEspacial.class));
    }

    @Test
    void testSaveInvalidData() {
        NaveEspacialDTO naveEspacialDTO = null;

        assertThrows(BadRequestException.class, () -> naveEspacialService.save(naveEspacialDTO));

        verify(naveEspacialRepository, times(0)).save(any(NaveEspacial.class));
    }

    @Test
    void testUpdate() {
        NaveEspacial naveEspacial = new NaveEspacial(1L, "Falcon");
        NaveEspacialDTO naveEspacialDTO = new NaveEspacialDTO(1L, "Falcon");
        when(naveEspacialRepository.save(any(NaveEspacial.class))).thenReturn(naveEspacial);

        NaveEspacialDTO result = naveEspacialService.update(naveEspacialDTO);

        assertEquals("Falcon", result.getName());
        verify(naveEspacialRepository, times(1)).save(any(NaveEspacial.class));
    }

    @Test
    void testDeleteById() {
        NaveEspacial naveEspacial = new NaveEspacial(1L, "Falcon");
        when(naveEspacialRepository.findById(1L)).thenReturn(Optional.of(naveEspacial));
        doNothing().when(naveEspacialRepository).deleteById(1L);

        naveEspacialService.deleteById(1L);

        verify(naveEspacialRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(naveEspacialRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> naveEspacialService.deleteById(1L));

        verify(naveEspacialRepository, times(1)).findById(1L);
    }
}
