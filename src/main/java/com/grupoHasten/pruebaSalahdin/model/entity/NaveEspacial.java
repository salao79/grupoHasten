package com.grupoHasten.pruebaSalahdin.model.entity;


import jakarta.persistence.*;
import lombok.*;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NaveEspacial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;


}