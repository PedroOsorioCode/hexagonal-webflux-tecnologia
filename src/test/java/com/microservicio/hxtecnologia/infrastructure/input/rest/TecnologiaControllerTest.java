package com.microservicio.hxtecnologia.infrastructure.input.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = TecnologiaController.class)
public class TecnologiaControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testHealthEndpoint() {
        webTestClient.get()
                .uri("/api/tecnologia/health")  // Cambia la URI si es diferente
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("ok");
    }
}
