package com.ststore.app.service.mapper;

import com.ststore.app.domain.*;
import com.ststore.app.service.dto.ImageAttributeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ImageAttribute and its DTO ImageAttributeDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ImageAttributeMapper extends EntityMapper<ImageAttributeDTO, ImageAttribute> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    ImageAttributeDTO toDto(ImageAttribute imageAttribute);

    @Mapping(source = "productId", target = "product")
    ImageAttribute toEntity(ImageAttributeDTO imageAttributeDTO);

    default ImageAttribute fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImageAttribute imageAttribute = new ImageAttribute();
        imageAttribute.setId(id);
        return imageAttribute;
    }
}
