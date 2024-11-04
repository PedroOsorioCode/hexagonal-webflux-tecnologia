package com.microservicio.hxtecnologia.infrastructure.out.r2dbc.repository;

import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.entity.CapacidadTecnologiaEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CapacidadTecnologiaRepository extends R2dbcRepository<CapacidadTecnologiaEntity, Long> {
    Flux<CapacidadTecnologiaEntity> findAllByIdCapacidadIn(List<Long> idCapacidades);
}
