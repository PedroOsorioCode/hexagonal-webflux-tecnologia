package com.microservicio.hxtecnologia.infrastructure.input.rest;

import com.microservicio.hxtecnologia.application.dto.request.CapacidadTecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaFilterRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.response.CapacidadResponseDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaPaginacionResponseDto;
import com.microservicio.hxtecnologia.application.dto.response.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.application.service.ICapacidadTecnologiaService;
import com.microservicio.hxtecnologia.application.service.ITecnologiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/tecnologia")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TecnologiaController {
    private final ITecnologiaService tecnologiaService;
    private final ICapacidadTecnologiaService capacidadTecnologiaService;

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
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Permite consultar las tecnologias ordenadas por nombre y paginadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informacion tecnologias", content = @Content),
            @ApiResponse(responseCode = "409", description = "Inconsistencia en la información", content = @Content)
    })
    @PostMapping("/listar")
    public Mono<ResponseEntity<TecnologiaPaginacionResponseDto<TecnologiaResponseDto>>> consultarTodos(
            @RequestBody Mono<TecnologiaFilterRequestDto> filter) {
        return tecnologiaService.consultarTodosPaginado(filter)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Permite relacionar una capacidad con varias tecnologías y obtener las tecnologias guardadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informacion tecnologias", content = @Content)
    })
    @PostMapping("/relacionar-capacidad-tecnologia")
    public Flux<TecnologiaResponseDto> relacionarCapacidadTecnologia(
            @RequestBody Mono<CapacidadTecnologiaRequestDto> capacidadTecnologiaRequestDTO) {

        return capacidadTecnologiaService.relacionarConCapacidad(capacidadTecnologiaRequestDTO);
    }

    @Operation(summary = "Permite consultas las tecnologias relacionadas a varias capacidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informacion tecnologias", content = @Content)
    })
    @PostMapping("/consultar-relacion-capacidad-tecnologia")
    public Flux<CapacidadResponseDto> consultarCapacidadTecnologia(
            @RequestBody List<Long> listaCapacidades) {

        return capacidadTecnologiaService.consultarCapacidadTecnologia(listaCapacidades);
    }
}
