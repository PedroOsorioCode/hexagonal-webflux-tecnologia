package com.microservicio.hxtecnologia.application.service.impl;

import com.microservicio.hxtecnologia.application.dto.request.CapacidadTecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.application.mapper.ITecnologiaModelMapper;
import com.microservicio.hxtecnologia.application.service.ICapacidadTecnologiaService;
import com.microservicio.hxtecnologia.domain.model.CapacidadTecnologiaModel;
import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import com.microservicio.hxtecnologia.domain.usecase.ICapacidadTecnologiaUseCasePort;
import com.microservicio.hxtecnologia.domain.usecase.ITecnologiaUseCasePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CapacidadTecnologiaService implements ICapacidadTecnologiaService {
    private final ICapacidadTecnologiaUseCasePort capacidadTecnologiaUseCasePort;
    private final ITecnologiaUseCasePort tecnologiaUseCasePort;
    private final ITecnologiaModelMapper tecnologiaModelMapper;

    @Override
    public Flux<TecnologiaResponseDto> relacionarConCapacidad(Mono<CapacidadTecnologiaRequestDto> capacidadTecnologiaRequestDTO) {
        return capacidadTecnologiaRequestDTO.flatMapMany(req -> {

            // Crea la lista de relaciones de CapacidadTecnologiaModel
            List<CapacidadTecnologiaModel> relacion = req.getIdTecnologia().stream()
                    .map(idTecnologia -> new CapacidadTecnologiaModel(req.getIdCapacidad(), idTecnologia))
                    .toList();

            // Llama a relacionarCapacidad y colecta los IDs de tecnología resultantes
            return capacidadTecnologiaUseCasePort.relacionarCapacidad(relacion)
                    .map(CapacidadTecnologiaModel::getIdTecnologia)
                    .collectList()
                    .flatMapMany(listaTecnologia -> {
                        // Llama a buscarTodosPorCodigo usando la lista completa de IDs
                        return tecnologiaUseCasePort.buscarTodosPorCodigo(listaTecnologia)
                                .map(tecnologiaModelMapper::toResponseFromModel);
                    });
        });
    }
}
