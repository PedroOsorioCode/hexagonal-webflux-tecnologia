package com.microservicio.hxtecnologia.application.service;

import com.microservicio.hxtecnologia.application.dto.request.CapacidadTecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.CapacidadResponseDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacidadTecnologiaService {
    Flux<TecnologiaResponseDto> relacionarConCapacidad(Mono<CapacidadTecnologiaRequestDto> capacidadTecnologiaRequestDTO);
    Flux<CapacidadResponseDto> consultarCapacidadTecnologia(List<Long> listaCapacidades);
}