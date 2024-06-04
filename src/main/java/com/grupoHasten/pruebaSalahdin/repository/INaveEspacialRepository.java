package com.grupoHasten.pruebaSalahdin.repository;

import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface INaveEspacialRepository extends PagingAndSortingRepository<NaveEspacial, Long> {

    Page<NaveEspacial> findAll(Pageable pageable);

    Optional<NaveEspacial> findById(Long id);

    Page<NaveEspacial> findByNameContaining(String name, Pageable pageable);

    NaveEspacial save(NaveEspacial naveEspacial);

    void deleteById(Long id);

    void deleteAll();

    int count();
}
