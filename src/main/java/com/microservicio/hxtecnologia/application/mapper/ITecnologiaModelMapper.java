package com.microservicio.hxtecnologia.application.mapper;

import com.microservicio.hxtecnologia.application.dto.request.TecnologiaRequestDto;
import com.microservicio.hxtecnologia.application.dto.request.TecnologiaResponseDto;
import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITecnologiaModelMapper {
    TecnologiaModel toModelFromRequest(TecnologiaRequestDto tecnologiaRequestDto);
    TecnologiaResponseDto toResponseFromModel(TecnologiaModel tecnologiaModel);
}
