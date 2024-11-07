package com.microservicio.hxtecnologia.infrastructure.out.r2dbc.repository;

import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.entity.TecnologiaEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface TecnologiaRepository extends R2dbcRepository<TecnologiaEntity, Long> {
    Mono<TecnologiaEntity> findByNombre(String nombre);
}
