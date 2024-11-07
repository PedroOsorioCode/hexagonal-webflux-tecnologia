package com.microservicio.hxtecnologia.domain.usecase.impl;

import com.microservicio.hxtecnologia.common.UseCase;
import com.microservicio.hxtecnologia.domain.model.CapacidadTecnologiaModel;
import com.microservicio.hxtecnologia.domain.serviceprovider.ICapacidadTecnologiaPersistencePort;
import com.microservicio.hxtecnologia.domain.usecase.ICapacidadTecnologiaUseCasePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class CapacidadTecnologiaUseCase implements ICapacidadTecnologiaUseCasePort {
    private final ICapacidadTecnologiaPersistencePort capacidadTecnologiaPersistencePort;
    @Override
    public Flux<CapacidadTecnologiaModel> relacionarCapacidad(List<CapacidadTecnologiaModel> listaCapacidadTecnologiaModel) {
        return capacidadTecnologiaPersistencePort.relacionarCapacidad(listaCapacidadTecnologiaModel);
    }

    @Override
    public Flux<CapacidadTecnologiaModel> consultarTecnologiaPorCapacidad(List<Long> listaCapacidades) {
        return capacidadTecnologiaPersistencePort.consultarTecnologiaPorCapacidad(listaCapacidades);
    }
}
