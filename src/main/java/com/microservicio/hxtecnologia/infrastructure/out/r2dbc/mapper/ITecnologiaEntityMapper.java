package com.microservicio.hxtecnologia.infrastructure.out.r2dbc.mapper;

import com.microservicio.hxtecnologia.domain.model.TecnologiaModel;
import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.entity.TecnologiaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITecnologiaEntityMapper {
    TecnologiaEntity toEntityFromModel(TecnologiaModel tecnologiaModel);
    TecnologiaModel toModelFronEntity(TecnologiaEntity tecnologiaEntity);
}
