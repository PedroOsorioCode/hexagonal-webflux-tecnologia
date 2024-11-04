package com.microservicio.hxtecnologia.infrastructure.out.r2dbc.adapter;

import com.microservicio.hxtecnologia.common.PersistenceAdapter;
import com.microservicio.hxtecnologia.domain.model.CapacidadTecnologiaModel;
import com.microservicio.hxtecnologia.domain.serviceprovider.ICapacidadTecnologiaPersistencePort;
import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.entity.CapacidadTecnologiaEntity;
import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.mapper.ICapacidadTecnologiaEntityMapper;
import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.repository.CapacidadTecnologiaRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

@PersistenceAdapter
@AllArgsConstructor
public class CapacidadTecnologiaR2dbcAdapter implements ICapacidadTecnologiaPersistencePort {
    private final CapacidadTecnologiaRepository capacidadTecnologiaRepository;
    private final ICapacidadTecnologiaEntityMapper capacidadTecnologiaEntityMapper;

    @Override
    public Flux<CapacidadTecnologiaModel> relacionarCapacidad(List<CapacidadTecnologiaModel> listaCapacidadTecnologiaModel) {
        List<CapacidadTecnologiaEntity> listaRelacion = capacidadTecnologiaEntityMapper.toEntityFromModelList(listaCapacidadTecnologiaModel);
        return capacidadTecnologiaRepository.saveAll(listaRelacion)
                .map(capacidadTecnologiaEntityMapper::toModelFromEntity);
    }

    @Override
    public Flux<CapacidadTecnologiaModel> consultarTecnologiaPorCapacidad(List<Long> listaCapacidades) {
        return capacidadTecnologiaRepository.findAllByIdCapacidadIn(listaCapacidades)
                .map(capacidadTecnologiaEntityMapper::toModelFromEntity);
    }
}
