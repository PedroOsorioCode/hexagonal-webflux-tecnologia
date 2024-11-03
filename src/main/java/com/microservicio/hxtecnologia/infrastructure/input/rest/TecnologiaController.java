package com.microservicio.hxtecnologia.infrastructure.input.rest;

import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.application.service.ITecnologiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tecnologia")
@RequiredArgsConstructor
public class TecnologiaController {
    private final ITecnologiaService tecnologiaService;

    @Operation(summary = "Validar la salud de la aplicación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Salud okey", content = @Content)
    })
    @GetMapping("/health")
    public Mono<String> health(){
        return Mono.just("ok");
    }

    @Operation(summary = "Permite registrar una nueva tecnología")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tecnología guardada correctamente", content = @Content),
        @ApiResponse(responseCode = "409", description = "Inconsistencia en la información", content = @Content)
    })
    @PostMapping
    public Mono<ResponseEntity<TecnologiaResponseDto>> guardar(@RequestBody Mono<TecnologiaRequestDto> tecnologiaRequestDto) {
        return tecnologiaService.guardar(tecnologiaRequestDto)
                .map(tecnologiaModel -> ResponseEntity.ok(tecnologiaModel))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
