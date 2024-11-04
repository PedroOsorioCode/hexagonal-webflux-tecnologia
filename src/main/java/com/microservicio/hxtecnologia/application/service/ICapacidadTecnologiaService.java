package com.microservicio.hxtecnologia.application.service;

import com.microservicio.hxtecnologia.application.dto.request.CapacidadTecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacidadTecnologiaService {
    Flux<TecnologiaResponseDto> relacionarConCapacidad(Mono<CapacidadTecnologiaRequestDto> capacidadTecnologiaRequestDTO);
}
