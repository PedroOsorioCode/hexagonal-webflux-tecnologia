package com.microservicio.hxtecnologia.infrastructure.out.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tecnologias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaEntity {
    @Id
    private Long id;
    private String nombre;
    private String descripcion;
}
