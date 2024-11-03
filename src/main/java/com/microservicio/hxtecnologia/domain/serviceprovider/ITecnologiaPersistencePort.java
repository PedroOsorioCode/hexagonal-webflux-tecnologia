package com.microservicio.hxtecnologia.domain.serviceprovider;

import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import reactor.core.publisher.Mono;

public interface ITecnologiaPersistencePort {
    Mono<TecnologiaModel> guardar(TecnologiaModel tecnologiaModel);
    Mono<Boolean> existePorNombre(String nombre);
}
