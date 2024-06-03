package com.grupoHasten.pruebaSalahdin.model.entity;


import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class NaveEspacial {

    @Id
    private UUID id;

    private String name;

    public NaveEspacial(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}