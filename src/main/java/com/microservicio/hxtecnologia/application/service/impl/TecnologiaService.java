package com.microservicio.hxtecnologia.application.service.impl;

import com.microservicio.hxtecnologia.application.common.ConstantesAplicacion;
import com.microservicio.hxtecnologia.application.common.MensajeError;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaFilterRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaPaginacionResponseDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.application.mapper.ITecnologiaModelMapper;
import com.microservicio.hxtecnologia.application.service.ITecnologiaService;
import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import com.microservicio.hxtecnologia.domain.usecase.ITecnologiaUseCasePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TecnologiaService implements ITecnologiaService {
    private final ITecnologiaModelMapper tecnologiaModelMapper;
    private final ITecnologiaUseCasePort tecnologiaUseCasePort;

    @Override
    public Mono<TecnologiaResponseDto> guardar(Mono<TecnologiaRequestDto> tecnologiaRequestDto) {
        return this.validarGuardar(tecnologiaRequestDto)
            .onErrorResume(Mono::error)
            .flatMap(data ->
                tecnologiaUseCasePort.guardar(data).map(tecnologiaModelMapper::toResponseFromModel)
            );
    }

    @Override
    public Mono<TecnologiaPaginacionResponseDto<TecnologiaResponseDto>>
        consultarTodosPaginado(Mono<TecnologiaFilterRequestDto> filter) {
        return filter.flatMap(condicion -> tecnologiaUseCasePort.consultarTodosPaginado()
            .switchIfEmpty(Mono.empty())
            .map(tecnologiaModelMapper::toResponseFromModel)
            .sort(condicion.getDireccionOrdenamiento().toLowerCase()
                    .equalsIgnoreCase(ConstantesAplicacion.METODO_ORDENAMIENTO_ASC)
                    ? Comparator.comparing(TecnologiaResponseDto::getNombre)
                    : Comparator.comparing(TecnologiaResponseDto::getNombre).reversed())
            .collectList()
            .flatMap(listaTecnologia -> {
                // Calcular la paginación
                int skip = condicion.getNumeroPagina() * condicion.getTamanoPorPagina();
                List<TecnologiaResponseDto> paginaTecnologias = listaTecnologia.stream()
                        .skip(skip)
                        .limit(condicion.getTamanoPorPagina())
                        .toList();

                // Crear el objeto de respuesta paginada
                TecnologiaPaginacionResponseDto<TecnologiaResponseDto> response = new TecnologiaPaginacionResponseDto<>(
                        paginaTecnologias,
                        condicion.getNumeroPagina(),
                        condicion.getTamanoPorPagina(),
                        listaTecnologia.size()
                );

                return Mono.just(response);
            }));

    }

    private Mono<TecnologiaModel> validarGuardar(Mono<TecnologiaRequestDto> request){
        return request.flatMap(req -> {
            String mensaje = "";
            if (req.getNombre().isEmpty())
                mensaje = MensajeError.DATOS_OBLIGATORIOS.formato(ConstantesAplicacion.NOMBRE);
            else if (req.getDescripcion().isEmpty())
                mensaje = MensajeError.DATOS_OBLIGATORIOS.formato(ConstantesAplicacion.DESCRIPCION);
            else if (req.getNombre().length() > ConstantesAplicacion.MAX_NOMBRE)
                mensaje = MensajeError.LONGITUD_PERMITIDA.formato(ConstantesAplicacion.NOMBRE, ConstantesAplicacion.MAX_NOMBRE);
            else if (req.getDescripcion().length() > ConstantesAplicacion.MAX_DESCRIPCION)
                mensaje = MensajeError.LONGITUD_PERMITIDA.formato(ConstantesAplicacion.DESCRIPCION, ConstantesAplicacion.MAX_DESCRIPCION);

            if (!mensaje.isEmpty()) {
                return Mono.error(new RuntimeException(mensaje));
            }

            return tecnologiaUseCasePort.existePorNombre(req.getNombre())
                .flatMap(existe -> Boolean.TRUE.equals(existe) ?
                    Mono.error(new RuntimeException(MensajeError.NOMBRE_DUPLICADO.getMensaje()))
                    : Mono.just(tecnologiaModelMapper.toModelFromRequest(req)));
        });
    }
}
