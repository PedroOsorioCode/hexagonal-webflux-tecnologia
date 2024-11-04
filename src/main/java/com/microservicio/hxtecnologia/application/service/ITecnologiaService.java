package com.microservicio.hxtecnologia.application.service;

import com.microservicio.hxtecnologia.application.dto.request.CapacidadTecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaFilterRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaPaginacionResponseDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaResponseDto;
import reactor.core.publisher.Mono;

public interface ITecnologiaService {
    Mono<TecnologiaResponseDto> guardar(Mono<TecnologiaRequestDto> tecnologiaRequestDto);
    Mono<TecnologiaPaginacionResponseDto<TecnologiaResponseDto>> consultarTodosPaginado(Mono<TecnologiaFilterRequestDto> filter);
}
