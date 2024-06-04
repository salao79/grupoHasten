package com.grupoHasten.pruebaSalahdin.service.impl;

import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import com.grupoHasten.pruebaSalahdin.repository.INaveEspacialRepository;
import com.grupoHasten.pruebaSalahdin.service.interfaces.BaseService;
import com.grupoHasten.pruebaSalahdin.service.interfaces.INaveEspacialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

@Service
public class NaveEspacialService extends BaseService implements INaveEspacialService {

    @Autowired
    private INaveEspacialRepository iNaveEspacialRepository;

    @Override
    public Page<NaveEspacialDTO> findAll(Pageable pageable) {

        Page<NaveEspacial> all = this.iNaveEspacialRepository.findAll(pageable);

        return super.mapPage(all, NaveEspacialDTO.class, pageable);
    }

    @Cacheable(value = "naves", key = "#id")
    @Override
    public NaveEspacialDTO findById(Long id) {

        NaveEspacial byId = this.iNaveEspacialRepository.findById(id);

        return super.map(byId, NaveEspacialDTO.class);
    }

    @Cacheable(value = "naves", key = "#id")
    @Override
    public Page<NaveEspacialDTO> findByNameContaining(String name, Pageable pageable) {

        Page<NaveEspacial> result = this.iNaveEspacialRepository.findByNameContaining(name,pageable);

        return super.mapPage(result, NaveEspacialDTO.class, pageable);
    }

    @Override
    public NaveEspacialDTO save(NaveEspacialDTO naveEspacialDTO) {

        NaveEspacial saved = this.iNaveEspacialRepository.save(super.map(naveEspacialDTO, NaveEspacial.class));

        return super.map(saved, NaveEspacialDTO.class);
    }

    @Override
    public NaveEspacialDTO update(NaveEspacialDTO naveEspacialDTO) {

        NaveEspacial updated = this.iNaveEspacialRepository.save(super.map(naveEspacialDTO, NaveEspacial.class));

        return super.map(updated, NaveEspacialDTO.class);
    }

    @Override
    public void deleteById(Long id) {

        this.iNaveEspacialRepository.deleteById(id);

    }
}
