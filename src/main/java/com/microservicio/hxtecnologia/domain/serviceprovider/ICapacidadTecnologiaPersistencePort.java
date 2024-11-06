package com.microservicio.hxtecnologia.domain.serviceprovider;

import com.microservicio.hxtecnologia.domain.model.CapacidadTecnologiaModel;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ICapacidadTecnologiaPersistencePort {
    Flux<CapacidadTecnologiaModel> relacionarCapacidad(List<CapacidadTecnologiaModel> listaCapacidadTecnologiaModel);
    Flux<CapacidadTecnologiaModel> consultarTecnologiaPorCapacidad(List<Long> listaCapacidades);
}
