package com.microservicio.hxtecnologia.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CapacidadResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private int cantidadTecnologia;
    private List<TecnologiaResponseDto> listaTecnologias;

    public CapacidadResponseDto(Long id, List<TecnologiaResponseDto> listaTecnologias) {
        this.id = id;
        this.listaTecnologias = listaTecnologias;
    }

    public CapacidadResponseDto(Long id) {
        this.id = id;
    }
}
