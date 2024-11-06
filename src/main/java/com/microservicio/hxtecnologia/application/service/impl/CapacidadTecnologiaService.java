package com.microservicio.hxtecnologia.application.service.impl;

import com.microservicio.hxtecnologia.application.dto.request.CapacidadTecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.CapacidadResponseDto;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                    .flatMapMany(listaTecnologia ->
                            tecnologiaUseCasePort.buscarTodosPorCodigo(listaTecnologia)
                                .map(tecnologiaModelMapper::toResponseFromModel)
                    );
        });
    }

    @Override
    public Flux<CapacidadResponseDto> consultarCapacidadTecnologia(List<Long> listaCapacidades) {
        Flux<CapacidadTecnologiaModel> capacidadTecnologiaModels = capacidadTecnologiaUseCasePort.consultarTecnologiaPorCapacidad(listaCapacidades);

        List<CapacidadResponseDto> listaResponse = listaCapacidades.stream()
                .map(CapacidadResponseDto::new).toList();

        Flux<TecnologiaModel> tecnologias = capacidadTecnologiaModels
                .map(CapacidadTecnologiaModel::getIdTecnologia)
                .distinct()
                .collectList()
                .flatMapMany(tecnologiaUseCasePort::buscarTodosPorCodigo);

        return tecnologias
                .collectList()
                .flatMapMany(listaTecnologias -> {
                    // Mapeamos a un Map<Long, String> para obtener el nombre de cada tecnología
                    Map<Long, String> tecnologiasMap = listaTecnologias.stream()
                            .collect(Collectors.toMap(TecnologiaModel::getId, TecnologiaModel::getNombre));

                    return capacidadTecnologiaModels
                            .filter(capacidadTecnologia -> tecnologiasMap.containsKey(capacidadTecnologia.getIdTecnologia()))
                            .groupBy(CapacidadTecnologiaModel::getIdCapacidad) // Agrupar por idCapacidad
                            .flatMap(groupedFlux ->
                                    groupedFlux.collectList().map(modelos -> {
                                        Long idCapacidad = modelos.get(0).getIdCapacidad();
                                        List<TecnologiaResponseDto> listaTecnologiasDto = modelos.stream()
                                                .map(modelo -> new TecnologiaResponseDto(
                                                        modelo.getIdTecnologia(),
                                                        tecnologiasMap.get(modelo.getIdTecnologia())
                                                ))
                                                .toList();

                                        // Buscar y actualizar la listaResponse con las tecnologías asociadas a cada capacidad
                                        return listaResponse.stream()
                                                .filter(capacidad -> capacidad.getId().equals(idCapacidad))
                                                .peek(capacidad -> capacidad.setListaTecnologias(listaTecnologiasDto))
                                                .findFirst()
                                                .orElse(null);
                                    })
                            );
                });
    }
}
