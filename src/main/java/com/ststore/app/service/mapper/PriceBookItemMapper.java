package com.ststore.app.service.mapper;

import com.ststore.app.domain.*;
import com.ststore.app.service.dto.PriceBookItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PriceBookItem and its DTO PriceBookItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, PriceBookMapper.class})
public interface PriceBookItemMapper extends EntityMapper<PriceBookItemDTO, PriceBookItem> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "priceBook.id", target = "priceBookId")
    @Mapping(source = "priceBook.priceBookName", target = "priceBookPriceBookName")
    PriceBookItemDTO toDto(PriceBookItem priceBookItem);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "priceBookId", target = "priceBook")
    PriceBookItem toEntity(PriceBookItemDTO priceBookItemDTO);

    default PriceBookItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceBookItem priceBookItem = new PriceBookItem();
        priceBookItem.setId(id);
        return priceBookItem;
    }
}
