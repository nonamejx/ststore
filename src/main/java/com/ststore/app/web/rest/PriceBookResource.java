package com.ststore.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ststore.app.service.PriceBookService;
import com.ststore.app.web.rest.errors.BadRequestAlertException;
import com.ststore.app.web.rest.util.HeaderUtil;
import com.ststore.app.service.dto.PriceBookDTO;
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
 * REST controller for managing PriceBook.
 */
@RestController
@RequestMapping("/api")
public class PriceBookResource {

    private final Logger log = LoggerFactory.getLogger(PriceBookResource.class);

    private static final String ENTITY_NAME = "priceBook";

    private final PriceBookService priceBookService;

    public PriceBookResource(PriceBookService priceBookService) {
        this.priceBookService = priceBookService;
    }

    /**
     * POST  /price-books : Create a new priceBook.
     *
     * @param priceBookDTO the priceBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priceBookDTO, or with status 400 (Bad Request) if the priceBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/price-books")
    @Timed
    public ResponseEntity<PriceBookDTO> createPriceBook(@Valid @RequestBody PriceBookDTO priceBookDTO) throws URISyntaxException {
        log.debug("REST request to save PriceBook : {}", priceBookDTO);
        if (priceBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new priceBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriceBookDTO result = priceBookService.save(priceBookDTO);
        return ResponseEntity.created(new URI("/api/price-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-books : Updates an existing priceBook.
     *
     * @param priceBookDTO the priceBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priceBookDTO,
     * or with status 400 (Bad Request) if the priceBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the priceBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/price-books")
    @Timed
    public ResponseEntity<PriceBookDTO> updatePriceBook(@Valid @RequestBody PriceBookDTO priceBookDTO) throws URISyntaxException {
        log.debug("REST request to update PriceBook : {}", priceBookDTO);
        if (priceBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriceBookDTO result = priceBookService.save(priceBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priceBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-books : get all the priceBooks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of priceBooks in body
     */
    @GetMapping("/price-books")
    @Timed
    public List<PriceBookDTO> getAllPriceBooks() {
        log.debug("REST request to get all PriceBooks");
        return priceBookService.findAll();
    }

    /**
     * GET  /price-books/:id : get the "id" priceBook.
     *
     * @param id the id of the priceBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priceBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/price-books/{id}")
    @Timed
    public ResponseEntity<PriceBookDTO> getPriceBook(@PathVariable Long id) {
        log.debug("REST request to get PriceBook : {}", id);
        Optional<PriceBookDTO> priceBookDTO = priceBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceBookDTO);
    }

    /**
     * DELETE  /price-books/:id : delete the "id" priceBook.
     *
     * @param id the id of the priceBookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/price-books/{id}")
    @Timed
    public ResponseEntity<Void> deletePriceBook(@PathVariable Long id) {
        log.debug("REST request to delete PriceBook : {}", id);
        priceBookService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/price-books?query=:query : search for the priceBook corresponding
     * to the query.
     *
     * @param query the query of the priceBook search
     * @return the result of the search
     */
    @GetMapping("/_search/price-books")
    @Timed
    public List<PriceBookDTO> searchPriceBooks(@RequestParam String query) {
        log.debug("REST request to search PriceBooks for query {}", query);
        return priceBookService.search(query);
    }

}
