package com.grupoHasten.pruebaSalahdin.controller;

import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import com.grupoHasten.pruebaSalahdin.service.impl.NaveEspacialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/naves")
public class NaveEspacialController {

    @Autowired
    private NaveEspacialService naveEspacialService;

    @GetMapping
    public Page<NaveEspacialDTO> findAll(Pageable pageable) {
        return naveEspacialService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public NaveEspacialDTO findById(@PathVariable Long id) {
        return naveEspacialService.findById(id);
    }

    @GetMapping("/search")
    public Page<NaveEspacialDTO> findByNameContaining(@RequestParam String name, Pageable pageable) {
        return naveEspacialService.findByNameContaining(name, pageable);
    }

    @PostMapping
    public NaveEspacialDTO save(@RequestBody NaveEspacialDTO naveEspacialDTO) {
        return naveEspacialService.save(naveEspacialDTO);
    }

    @PutMapping("/{id}")
    public NaveEspacialDTO update(@PathVariable String id, @RequestBody NaveEspacialDTO naveEspacialDTO) {
        naveEspacialDTO.setId(Long.parseLong(id)); // Ensure the ID is set correctly
        return naveEspacialService.update(naveEspacialDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        naveEspacialService.deleteById(id);
    }
}
