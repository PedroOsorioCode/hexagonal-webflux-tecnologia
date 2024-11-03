package com.microservicio.hxtecnologia.application.service;

import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaResponseDto;
import reactor.core.publisher.Mono;

public interface ITecnologiaService {
    Mono<TecnologiaResponseDto> guardar(Mono<TecnologiaRequestDto> tecnologiaRequestDto);
}
