package com.microservicio.hxtecnologia.infrastructure.out.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "capacidad_tecnologia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapacidadTecnologiaEntity {
    @Id
    private Long id;
    private Long idCapacidad;
    private Long idTecnologia;
}
