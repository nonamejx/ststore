package com.ststore.app.service.mapper;

import com.ststore.app.domain.*;
import com.ststore.app.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UnitMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryCategoryName")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.unitName", target = "unitUnitName")
    ProductDTO toDto(Product product);

    @Mapping(target = "imageAttributes", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "unitId", target = "unit")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
