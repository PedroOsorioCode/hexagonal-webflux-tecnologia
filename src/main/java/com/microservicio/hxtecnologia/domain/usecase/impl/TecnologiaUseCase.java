package com.microservicio.hxtecnologia.domain.usecase.impl;

import com.microservicio.hxtecnologia.common.UseCase;
import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import com.microservicio.hxtecnologia.domain.serviceprovider.ITecnologiaPersistencePort;
import com.microservicio.hxtecnologia.domain.usecase.ITecnologiaUseCasePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@UseCase
@RequiredArgsConstructor
public class TecnologiaUseCase implements ITecnologiaUseCasePort {
    private final ITecnologiaPersistencePort tecnologiaPersistencePort;
    @Override
    public Mono<TecnologiaModel> guardar(TecnologiaModel tecnologiaModel) {
        return tecnologiaPersistencePort.guardar(tecnologiaModel);
    }

    @Override
    public Mono<Boolean> existePorNombre(String nombre) {
        return tecnologiaPersistencePort.existePorNombre(nombre);
    }

    @Override
    public Flux<TecnologiaModel> consultarTodosPaginado() {
        return tecnologiaPersistencePort.consultarTodos();
    }
}
