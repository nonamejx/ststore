package com.ststore.app.service.mapper;

import com.ststore.app.domain.*;
import com.ststore.app.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "father.id", target = "fatherId")
    @Mapping(source = "father.categoryName", target = "fatherCategoryName")
    CategoryDTO toDto(Category category);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(source = "fatherId", target = "father")
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
