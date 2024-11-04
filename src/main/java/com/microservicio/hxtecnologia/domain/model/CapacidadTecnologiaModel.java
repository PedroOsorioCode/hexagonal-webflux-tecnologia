package com.microservicio.hxtecnologia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CapacidadTecnologiaModel {
    private Long id;
    private Long idCapacidad;
    private Long idTecnologia;

    public CapacidadTecnologiaModel(Long idCapacidad, Long idTecnologia) {
        this.idCapacidad = idCapacidad;
        this.idTecnologia = idTecnologia;
    }
}
