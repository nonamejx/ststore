package com.ststore.app.service.mapper;

import com.ststore.app.domain.*;
import com.ststore.app.service.dto.PriceBookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PriceBook and its DTO PriceBookDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriceBookMapper extends EntityMapper<PriceBookDTO, PriceBook> {



    default PriceBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceBook priceBook = new PriceBook();
        priceBook.setId(id);
        return priceBook;
    }
}
