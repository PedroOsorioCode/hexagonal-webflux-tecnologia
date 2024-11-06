package com.microservicio.hxtecnologia.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TecnologiaResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;

    public TecnologiaResponseDto(Long idTecnologia, String nombre) {
        this.id = idTecnologia;
        this.nombre = nombre;
    }
}
