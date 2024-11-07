package com.microservicio.hxtecnologia.domain.serviceprovider;

import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITecnologiaPersistencePort {
    Mono<TecnologiaModel> guardar(TecnologiaModel tecnologiaModel);
    Mono<Boolean> existePorNombre(String nombre);
    Flux<TecnologiaModel> consultarTodos();
    Flux<TecnologiaModel> buscarTodosPorCodigo(List<Long> listaId);
}
