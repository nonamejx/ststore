package com.ststore.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ststore.app.service.PriceBookItemService;
import com.ststore.app.web.rest.errors.BadRequestAlertException;
import com.ststore.app.web.rest.util.HeaderUtil;
import com.ststore.app.service.dto.PriceBookItemDTO;
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
 * REST controller for managing PriceBookItem.
 */
@RestController
@RequestMapping("/api")
public class PriceBookItemResource {

    private final Logger log = LoggerFactory.getLogger(PriceBookItemResource.class);

    private static final String ENTITY_NAME = "priceBookItem";

    private final PriceBookItemService priceBookItemService;

    public PriceBookItemResource(PriceBookItemService priceBookItemService) {
        this.priceBookItemService = priceBookItemService;
    }

    /**
     * POST  /price-book-items : Create a new priceBookItem.
     *
     * @param priceBookItemDTO the priceBookItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priceBookItemDTO, or with status 400 (Bad Request) if the priceBookItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/price-book-items")
    @Timed
    public ResponseEntity<PriceBookItemDTO> createPriceBookItem(@Valid @RequestBody PriceBookItemDTO priceBookItemDTO) throws URISyntaxException {
        log.debug("REST request to save PriceBookItem : {}", priceBookItemDTO);
        if (priceBookItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new priceBookItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriceBookItemDTO result = priceBookItemService.save(priceBookItemDTO);
        return ResponseEntity.created(new URI("/api/price-book-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-book-items : Updates an existing priceBookItem.
     *
     * @param priceBookItemDTO the priceBookItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priceBookItemDTO,
     * or with status 400 (Bad Request) if the priceBookItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the priceBookItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/price-book-items")
    @Timed
    public ResponseEntity<PriceBookItemDTO> updatePriceBookItem(@Valid @RequestBody PriceBookItemDTO priceBookItemDTO) throws URISyntaxException {
        log.debug("REST request to update PriceBookItem : {}", priceBookItemDTO);
        if (priceBookItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriceBookItemDTO result = priceBookItemService.save(priceBookItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priceBookItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-book-items : get all the priceBookItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of priceBookItems in body
     */
    @GetMapping("/price-book-items")
    @Timed
    public List<PriceBookItemDTO> getAllPriceBookItems() {
        log.debug("REST request to get all PriceBookItems");
        return priceBookItemService.findAll();
    }

    /**
     * GET  /price-book-items/:id : get the "id" priceBookItem.
     *
     * @param id the id of the priceBookItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priceBookItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/price-book-items/{id}")
    @Timed
    public ResponseEntity<PriceBookItemDTO> getPriceBookItem(@PathVariable Long id) {
        log.debug("REST request to get PriceBookItem : {}", id);
        Optional<PriceBookItemDTO> priceBookItemDTO = priceBookItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceBookItemDTO);
    }

    /**
     * DELETE  /price-book-items/:id : delete the "id" priceBookItem.
     *
     * @param id the id of the priceBookItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/price-book-items/{id}")
    @Timed
    public ResponseEntity<Void> deletePriceBookItem(@PathVariable Long id) {
        log.debug("REST request to delete PriceBookItem : {}", id);
        priceBookItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/price-book-items?query=:query : search for the priceBookItem corresponding
     * to the query.
     *
     * @param query the query of the priceBookItem search
     * @return the result of the search
     */
    @GetMapping("/_search/price-book-items")
    @Timed
    public List<PriceBookItemDTO> searchPriceBookItems(@RequestParam String query) {
        log.debug("REST request to search PriceBookItems for query {}", query);
        return priceBookItemService.search(query);
    }

}
