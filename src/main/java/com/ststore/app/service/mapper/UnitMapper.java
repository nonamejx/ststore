package com.ststore.app.service.mapper;

import com.ststore.app.domain.*;
import com.ststore.app.service.dto.UnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Unit and its DTO UnitDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {


    @Mapping(target = "products", ignore = true)
    Unit toEntity(UnitDTO unitDTO);

    default Unit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(id);
        return unit;
    }
}
