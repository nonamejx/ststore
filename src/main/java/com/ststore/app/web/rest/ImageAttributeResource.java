package com.ststore.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ststore.app.service.ImageAttributeService;
import com.ststore.app.web.rest.errors.BadRequestAlertException;
import com.ststore.app.web.rest.util.HeaderUtil;
import com.ststore.app.service.dto.ImageAttributeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ImageAttribute.
 */
@RestController
@RequestMapping("/api")
public class ImageAttributeResource {

    private final Logger log = LoggerFactory.getLogger(ImageAttributeResource.class);

    private static final String ENTITY_NAME = "imageAttribute";

    private final ImageAttributeService imageAttributeService;

    public ImageAttributeResource(ImageAttributeService imageAttributeService) {
        this.imageAttributeService = imageAttributeService;
    }

    /**
     * POST  /image-attributes : Create a new imageAttribute.
     *
     * @param imageAttributeDTO the imageAttributeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imageAttributeDTO, or with status 400 (Bad Request) if the imageAttribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/image-attributes")
    @Timed
    public ResponseEntity<ImageAttributeDTO> createImageAttribute(@Valid @RequestBody ImageAttributeDTO imageAttributeDTO) throws URISyntaxException {
        log.debug("REST request to save ImageAttribute : {}", imageAttributeDTO);
        if (imageAttributeDTO.getId() != null) {
            throw new BadRequestAlertException("A new imageAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageAttributeDTO result = imageAttributeService.save(imageAttributeDTO);
        return ResponseEntity.created(new URI("/api/image-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /image-attributes : Updates an existing imageAttribute.
     *
     * @param imageAttributeDTO the imageAttributeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imageAttributeDTO,
     * or with status 400 (Bad Request) if the imageAttributeDTO is not valid,
     * or with status 500 (Internal Server Error) if the imageAttributeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/image-attributes")
    @Timed
    public ResponseEntity<ImageAttributeDTO> updateImageAttribute(@Valid @RequestBody ImageAttributeDTO imageAttributeDTO) throws URISyntaxException {
        log.debug("REST request to update ImageAttribute : {}", imageAttributeDTO);
        if (imageAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImageAttributeDTO result = imageAttributeService.save(imageAttributeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imageAttributeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /image-attributes : get all the imageAttributes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imageAttributes in body
     */
    @GetMapping("/image-attributes")
    @Timed
    public List<ImageAttributeDTO> getAllImageAttributes() {
        log.debug("REST request to get all ImageAttributes");
        return imageAttributeService.findAll();
    }

    /**
     * GET  /image-attributes/:id : get the "id" imageAttribute.
     *
     * @param id the id of the imageAttributeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageAttributeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/image-attributes/{id}")
    @Timed
    public ResponseEntity<ImageAttributeDTO> getImageAttribute(@PathVariable Long id) {
        log.debug("REST request to get ImageAttribute : {}", id);
        Optional<ImageAttributeDTO> imageAttributeDTO = imageAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageAttributeDTO);
    }

    /**
     * DELETE  /image-attributes/:id : delete the "id" imageAttribute.
     *
     * @param id the id of the imageAttributeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/image-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteImageAttribute(@PathVariable Long id) {
        log.debug("REST request to delete ImageAttribute : {}", id);
        imageAttributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/image-attributes?query=:query : search for the imageAttribute corresponding
     * to the query.
     *
     * @param query the query of the imageAttribute search
     * @return the result of the search
     */
    @GetMapping("/_search/image-attributes")
    @Timed
    public List<ImageAttributeDTO> searchImageAttributes(@RequestParam String query) {
        log.debug("REST request to search ImageAttributes for query {}", query);
        return imageAttributeService.search(query);
    }

}
