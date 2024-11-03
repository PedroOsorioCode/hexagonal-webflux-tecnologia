package com.microservicio.hxtecnologia.infrastructure.input.rest;

import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.application.service.impl.TecnologiaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(controllers = TecnologiaController.class)
public class TecnologiaControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private TecnologiaService tecnologiaService;

    @Test
    public void testHealth() {
        webTestClient.get()
                .uri("/api/tecnologia/health")  // Cambia la URI si es diferente
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("ok");
    }

    @Test
    public void testGuardarExitoso() {
        // Preparar datos de prueba
        TecnologiaRequestDto requestDto = new TecnologiaRequestDto();
        requestDto.setNombre("Tecnologia Prueba");
        requestDto.setDescripcion("Descripción de prueba");

        TecnologiaResponseDto responseDto = new TecnologiaResponseDto();
        responseDto.setNombre("Tecnologia Prueba");
        responseDto.setDescripcion("Descripción de prueba");

        // Mockear el comportamiento del servicio para que devuelva una respuesta exitosa
        when(tecnologiaService.guardar(any(Mono.class))).thenReturn(Mono.just(responseDto));

        // Ejecutar la prueba
        webTestClient.post()
                .uri("/api/tecnologia") // Cambia el URI si es diferente
                .contentType(APPLICATION_JSON)
                .body(Mono.just(requestDto), TecnologiaRequestDto.class)
                .exchange()
                .expectStatus().isOk();
    }
}
