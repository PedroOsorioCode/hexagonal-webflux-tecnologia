package com.microservicio.hxtecnologia.domain.serviceprovider;

import com.microservicio.hxtecnologia.domain.model.CapacidadTecnologiaModel;
import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ICapacidadTecnologiaPersistencePort {
    Flux<CapacidadTecnologiaModel> relacionarCapacidad(List<CapacidadTecnologiaModel> listaCapacidadTecnologiaModel);

}
