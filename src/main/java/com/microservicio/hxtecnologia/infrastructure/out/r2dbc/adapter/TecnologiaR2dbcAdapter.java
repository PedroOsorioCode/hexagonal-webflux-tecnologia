package com.microservicio.hxtecnologia.infrastructure.out.r2dbc.adapter;

import com.microservicio.hxtecnologia.common.PersistenceAdapter;
import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import com.microservicio.hxtecnologia.domain.serviceprovider.ITecnologiaPersistencePort;
import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.mapper.ITecnologiaEntityMapper;
import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.repository.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class TecnologiaR2dbcAdapter implements ITecnologiaPersistencePort {
    private final ITecnologiaEntityMapper tecnologiaEntityMapper;
    private final TecnologiaRepository tecnologiaRepository;
    @Override
    public Mono<TecnologiaModel> guardar(TecnologiaModel tecnologiaModel) {
        return tecnologiaRepository.save(tecnologiaEntityMapper.toEntityFromModel(tecnologiaModel))
                .map(tecnologiaEntityMapper::toModelFronEntity);
    }

    @Override
    public Mono<Boolean> existePorNombre(String nombre) {
        return tecnologiaRepository.findByNombre(nombre).hasElement();
    }

    @Override
    public Flux<TecnologiaModel> consultarTodos() {
        return tecnologiaRepository.findAll().map(tecnologiaEntityMapper::toModelFronEntity);
    }
}
