package com.microservicio.hxtecnologia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecnologiaModel {
    private Long id;
    private String nombre;
    private String descripcion;
}
