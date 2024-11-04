package com.microservicio.hxtecnologia.domain.usecase;

import com.microservicio.hxtecnologia.domain.model.CapacidadTecnologiaModel;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ICapacidadTecnologiaUseCasePort {
    Flux<CapacidadTecnologiaModel> relacionarCapacidad(List<CapacidadTecnologiaModel> listaCapacidadTecnologiaModel);
}
