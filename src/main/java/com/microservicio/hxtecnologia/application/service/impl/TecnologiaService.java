package com.microservicio.hxtecnologia.application.service.impl;

import com.microservicio.hxtecnologia.application.common.ConstantesAplicacion;
import com.microservicio.hxtecnologia.application.common.MensajeError;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.application.mapper.ITecnologiaModelMapper;
import com.microservicio.hxtecnologia.application.service.ITecnologiaService;
import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import com.microservicio.hxtecnologia.domain.usecase.ITecnologiaUseCasePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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

    private Mono<TecnologiaModel> validarGuardar(Mono<TecnologiaRequestDto> request){
        return request.flatMap(req -> {
            String mensaje = "";
            if (req.getNombre().isEmpty())
                mensaje = MensajeError.DATOS_OBLIGATORIOS.formato(ConstantesAplicacion.NOMBRE);
            if (req.getDescripcion().isEmpty())
                mensaje = MensajeError.DATOS_OBLIGATORIOS.formato(ConstantesAplicacion.DESCRIPCION);
            if (req.getNombre().length() > ConstantesAplicacion.MAX_NOMBRE)
                mensaje = MensajeError.LONGITUD_PERMITIDA.formato(ConstantesAplicacion.NOMBRE, ConstantesAplicacion.MAX_NOMBRE);
            if (req.getDescripcion().length() > ConstantesAplicacion.MAX_DESCRIPCION)
                mensaje = MensajeError.LONGITUD_PERMITIDA.formato(ConstantesAplicacion.DESCRIPCION, ConstantesAplicacion.MAX_DESCRIPCION);

            if (!mensaje.isEmpty()) {
                return Mono.error(new RuntimeException(mensaje));
            }

            return tecnologiaUseCasePort.existePorNombre(req.getNombre())
                .flatMap(existe -> {
                    return existe ?
                        Mono.error(new RuntimeException(MensajeError.NOMBRE_DUPLICADO.getMensaje()))
                        : Mono.just(tecnologiaModelMapper.toModelFromRequest(req));
                });
        });
    }
}
