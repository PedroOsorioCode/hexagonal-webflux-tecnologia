package com.microservicio.hxtecnologia.infrastructure.out.r2dbc.mapper;

import com.microservicio.hxtecnologia.domain.model.CapacidadTecnologiaModel;
import com.microservicio.hxtecnologia.infrastructure.out.r2dbc.entity.CapacidadTecnologiaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICapacidadTecnologiaEntityMapper {
    List<CapacidadTecnologiaEntity> toEntityFromModelList(List<CapacidadTecnologiaModel> listaCapacidadTecnologia);
    CapacidadTecnologiaModel toModelFromEntity(CapacidadTecnologiaEntity capacidadTecnologia);
}
