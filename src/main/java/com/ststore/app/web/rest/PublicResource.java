package com.ststore.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ststore.app.service.CategoryService;
import com.ststore.app.service.ProductService;
import com.ststore.app.service.dto.CategoryDTO;
import com.ststore.app.service.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;
    private final ProductService productService;

    public PublicResource(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    /**
     * GET  /categories : get all the categories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categories in body
     */
    @GetMapping("/categories")
    @Timed
    public List<CategoryDTO> getAllCategories() {
        log.debug("REST request to get all Categories in PUBLIC");
        return categoryService.findAll();
    }

    /**
     * GET  /products : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/products")
    @Timed
    public List<ProductDTO> getAllProducts() {
        log.debug("REST request to get all Products in PUBLIC");
        return productService.findAll();
    }

}
