package com.microservicio.hxtecnologia.domain.usecase;

import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITecnologiaUseCasePort {
    Mono<TecnologiaModel> guardar(TecnologiaModel tecnologiaModel);
    Mono<Boolean> existePorNombre(String nombre);
    Flux<TecnologiaModel> consultarTodosPaginado();
    Flux<TecnologiaModel> buscarTodosPorCodigo(List<Long> listaId);
}
