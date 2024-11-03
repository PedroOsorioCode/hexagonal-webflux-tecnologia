package com.microservicio.hxtecnologia.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecnologiaResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
