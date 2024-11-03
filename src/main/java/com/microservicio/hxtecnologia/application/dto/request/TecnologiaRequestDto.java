package com.microservicio.hxtecnologia.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaRequestDto {
    private String nombre;
    private String descripcion;
}
