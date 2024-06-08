package com.grupoHasten.pruebaSalahdin.service.impl.businesslogic;

import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import com.grupoHasten.pruebaSalahdin.model.dto.exception.BadRequestException;
import com.grupoHasten.pruebaSalahdin.model.dto.exception.ResourceNotFoundException;
import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import com.grupoHasten.pruebaSalahdin.repository.INaveEspacialRepository;
import com.grupoHasten.pruebaSalahdin.service.impl.BaseService;
import com.grupoHasten.pruebaSalahdin.service.interfaces.broker.IBrokerService;
import com.grupoHasten.pruebaSalahdin.service.interfaces.businesslogic.INaveEspacialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NaveEspacialService extends BaseService implements INaveEspacialService {

    @Autowired
    private INaveEspacialRepository iNaveEspacialRepository;

    @Autowired

    private IBrokerService brokerService;


    @Override
    public Page<NaveEspacialDTO> findAll(Pageable pageable) {
        Page<NaveEspacial> all = this.iNaveEspacialRepository.findAll(pageable);
        return super.mapPage(all, NaveEspacialDTO.class, pageable);
    }

    @Cacheable(value = "naves", key = "#id")
    @Override
    public NaveEspacialDTO findById(Long id) {
        NaveEspacial byId = this.iNaveEspacialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nave Espacial not found for this id :: " + id));
        return super.map(byId, NaveEspacialDTO.class);
    }

    @Cacheable(value = "naves", key = "#name")
    @Override
    public Page<NaveEspacialDTO> findByNameContaining(String name, Pageable pageable) {
        Page<NaveEspacial> result = this.iNaveEspacialRepository.findByNameContaining(name, pageable);
        return super.mapPage(result, NaveEspacialDTO.class, pageable);
    }

    @Override
    public NaveEspacialDTO save(NaveEspacialDTO naveEspacialDTO) {
        if(naveEspacialDTO == null) {
            throw new BadRequestException("Nave espacial is null");
        }
        NaveEspacial saved = this.iNaveEspacialRepository.save(super.map(naveEspacialDTO, NaveEspacial.class));
        System.out.println("Enviando a kafka");
        brokerService.sendNaveEspacialToBroker(saved);
        return super.map(saved, NaveEspacialDTO.class);
    }

    @Override
    public NaveEspacialDTO update(NaveEspacialDTO naveEspacialDTO) {
        if(naveEspacialDTO == null) {
            throw new BadRequestException("Nave espacial is null");
        }
        NaveEspacial updated = this.iNaveEspacialRepository.save(super.map(naveEspacialDTO, NaveEspacial.class));
        return super.map(updated, NaveEspacialDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        this.iNaveEspacialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nave Espacial not found for this id :: " + id));
        this.iNaveEspacialRepository.deleteById(id);
    }
}
