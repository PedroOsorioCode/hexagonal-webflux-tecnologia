package com.microservicio.hxtecnologia.domain.usecase;

import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import reactor.core.publisher.Mono;

public interface ITecnologiaUseCasePort {
    Mono<TecnologiaModel> guardar(TecnologiaModel tecnologiaModel);
    Mono<Boolean> existePorNombre(String nombre);
}
