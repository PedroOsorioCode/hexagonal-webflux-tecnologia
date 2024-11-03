package com.microservicio.hxtecnologia.application.service;

import com.microservicio.hxtecnologia.application.common.ConstantesAplicacion;
import com.microservicio.hxtecnologia.application.common.MensajeError;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.application.mapper.ITecnologiaModelMapper;
import com.microservicio.hxtecnologia.application.service.impl.TecnologiaService;
import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import com.microservicio.hxtecnologia.domain.usecase.impl.TecnologiaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TecnologiaServiceTest {
    @InjectMocks
    private TecnologiaService tecnologiaService;
    @Mock
    private TecnologiaUseCase tecnologiaUseCase;
    @Mock
    private ITecnologiaModelMapper tecnologiaModelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test public void testValidarGuardarNombreVacio() {
        TecnologiaRequestDto requestDto = new TecnologiaRequestDto();
        requestDto.setNombre("");
        requestDto.setDescripcion("Descripción");

        Mono<TecnologiaRequestDto> requestMono = Mono.just(requestDto);

        StepVerifier.create(tecnologiaService.guardar(requestMono))
            .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                throwable.getMessage().equals(MensajeError.DATOS_OBLIGATORIOS.formato(ConstantesAplicacion.NOMBRE)))
            .verify();
    }

    @Test
    public void testValidarGuardarDescripcionVacia() {
        TecnologiaRequestDto requestDto = new TecnologiaRequestDto();
        requestDto.setNombre("Tecnologia");
        requestDto.setDescripcion("");

        Mono<TecnologiaRequestDto> requestMono = Mono.just(requestDto);

        StepVerifier.create(tecnologiaService.guardar(requestMono))
            .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                    throwable.getMessage().equals(MensajeError.DATOS_OBLIGATORIOS.formato(ConstantesAplicacion.DESCRIPCION)))
            .verify();
    }

    @Test
    public void testValidarGuardarNombreLargo() {
        TecnologiaRequestDto requestDto = new TecnologiaRequestDto();
        requestDto.setNombre(String.format("%-51s", "Texto").replace(' ', 'a'));
        requestDto.setDescripcion("Descripción");

        Mono<TecnologiaRequestDto> requestMono = Mono.just(requestDto);

        StepVerifier.create(tecnologiaService.guardar(requestMono))
            .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                    throwable.getMessage().equals(MensajeError.LONGITUD_PERMITIDA.formato(ConstantesAplicacion.NOMBRE, ConstantesAplicacion.MAX_NOMBRE)))
            .verify();
    }

    @Test
    public void testValidarGuardarDescripcionLarga() {
        TecnologiaRequestDto requestDto = new TecnologiaRequestDto();
        requestDto.setNombre("Tecnologia");
        requestDto.setDescripcion(String.format("%-91s", "Texto").replace(' ', 'a')); // Excede la longitud

        Mono<TecnologiaRequestDto> requestMono = Mono.just(requestDto);

        StepVerifier.create(tecnologiaService.guardar(requestMono))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(MensajeError.LONGITUD_PERMITIDA.formato(ConstantesAplicacion.DESCRIPCION, ConstantesAplicacion.MAX_DESCRIPCION)))
                .verify();
    }

    @Test
    public void testValidarGuardarNombreDuplicado() {
        // Crear instancias de request y model para simular la transformación
        TecnologiaRequestDto requestDto = new TecnologiaRequestDto();
        requestDto.setNombre("NombrePrueba");
        requestDto.setDescripcion("Descripción de prueba");

        TecnologiaModel expectedModel = new TecnologiaModel();
        expectedModel.setNombre("NombrePrueba");
        expectedModel.setDescripcion("Descripción de prueba");

        // Mockear el comportamiento de mapeo
        when(tecnologiaModelMapper.toModelFromRequest(requestDto)).thenReturn(expectedModel);
        when(tecnologiaUseCase.existePorNombre(any())).thenReturn(Mono.just(true));

        Mono<TecnologiaRequestDto> requestMono = Mono.just(requestDto);
        StepVerifier.create(tecnologiaService.guardar(requestMono))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals(MensajeError.NOMBRE_DUPLICADO.getMensaje()))
                .verify();

        verify(tecnologiaUseCase, times(1)).existePorNombre(any());
    }

    @Test
    public void testGuardarCasoExitoso() {
        // Crear instancias de request y model para simular la transformación
        TecnologiaRequestDto requestDto = new TecnologiaRequestDto();
        requestDto.setNombre("NombrePrueba");
        requestDto.setDescripcion("Descripción de prueba");

        TecnologiaResponseDto responseDto = new TecnologiaResponseDto();
        responseDto.setId(1L);

        TecnologiaModel expectedModel = new TecnologiaModel();
        expectedModel.setNombre("NombrePrueba");
        expectedModel.setDescripcion("Descripción de prueba");

        // Mockear el comportamiento de mapeo
        when(tecnologiaModelMapper.toModelFromRequest(requestDto)).thenReturn(expectedModel);
        when(tecnologiaUseCase.existePorNombre(any())).thenReturn(Mono.just(false));
        when(tecnologiaUseCase.guardar(any())).thenReturn(Mono.just(new TecnologiaModel(1L, "a", "b")));
        when(tecnologiaModelMapper.toResponseFromModel(any())).thenReturn(responseDto);

        Mono<TecnologiaRequestDto> requestMono = Mono.just(requestDto);
        Mono<TecnologiaResponseDto> responseMono = tecnologiaService.guardar(requestMono);

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response instanceof TecnologiaResponseDto)
                .verifyComplete();
    }
}