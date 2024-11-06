package com.microservicio.hxtecnologia.infrastructure.input.rest;

import com.microservicio.hxtecnologia.application.dto.request.TecnologiaFilterRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaPaginacionResponseDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.application.service.ICapacidadTecnologiaService;
import com.microservicio.hxtecnologia.application.service.impl.CapacidadTecnologiaService;
import com.microservicio.hxtecnologia.application.service.impl.TecnologiaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(controllers = TecnologiaController.class)
class TecnologiaControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private TecnologiaService tecnologiaService;
    @MockBean
    private CapacidadTecnologiaService capacidadTecnologiaService;

    private String nombre = "test";
    private String descripcion = "testd";

    @Test
    void testHealth() {
        webTestClient.get()
                .uri("/api/tecnologia/health")  // Cambia la URI si es diferente
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("ok");
    }

    @Test
    void testGuardarExitoso() {
        // Preparar datos de prueba
        TecnologiaRequestDto requestDto = TecnologiaRequestDto
                .builder().nombre(this.nombre).descripcion(this.descripcion).build();

        TecnologiaResponseDto responseDto = TecnologiaResponseDto
                .builder().nombre(this.nombre).descripcion(this.descripcion).build();

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

    @Test
    void testListarExitoso() {
        TecnologiaFilterRequestDto filterDto = new TecnologiaFilterRequestDto(); // Configura el filtro según sea necesario
        TecnologiaResponseDto responseDto = new TecnologiaResponseDto(); // Configura la respuesta según sea necesario
        TecnologiaPaginacionResponseDto<TecnologiaResponseDto> paginacionResponseDto = new TecnologiaPaginacionResponseDto<>();
        paginacionResponseDto.setContent(List.of(responseDto)); // Agrega elementos a la paginación

        when(tecnologiaService.consultarTodosPaginado(any())).thenReturn(Mono.just(paginacionResponseDto));

        webTestClient.post()
                .uri("/api/tecnologia/listar") // Cambia el URI si es diferente
                .contentType(APPLICATION_JSON)
                .body(Mono.just(filterDto), TecnologiaRequestDto.class)
                .exchange()
                .expectStatus().isOk();
    }
}
