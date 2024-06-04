package com.grupoHasten.pruebaSalahdin.service.interfaces;

import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INaveEspacialService {

    Page<NaveEspacialDTO> findAll(Pageable pageable);

    NaveEspacialDTO findById(Long id);

    Page<NaveEspacialDTO> findByNameContaining(String name, Pageable pageable);

    NaveEspacialDTO save(NaveEspacialDTO naveEspacialDTO);

    NaveEspacialDTO update(NaveEspacialDTO naveEspacialDTO);

    void deleteById(Long id);
}
