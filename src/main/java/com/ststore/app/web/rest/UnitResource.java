package com.ststore.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ststore.app.service.UnitService;
import com.ststore.app.web.rest.errors.BadRequestAlertException;
import com.ststore.app.web.rest.util.HeaderUtil;
import com.ststore.app.service.dto.UnitDTO;
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
 * REST controller for managing Unit.
 */
@RestController
@RequestMapping("/api")
public class UnitResource {

    private final Logger log = LoggerFactory.getLogger(UnitResource.class);

    private static final String ENTITY_NAME = "unit";

    private final UnitService unitService;

    public UnitResource(UnitService unitService) {
        this.unitService = unitService;
    }

    /**
     * POST  /units : Create a new unit.
     *
     * @param unitDTO the unitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unitDTO, or with status 400 (Bad Request) if the unit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/units")
    @Timed
    public ResponseEntity<UnitDTO> createUnit(@Valid @RequestBody UnitDTO unitDTO) throws URISyntaxException {
        log.debug("REST request to save Unit : {}", unitDTO);
        if (unitDTO.getId() != null) {
            throw new BadRequestAlertException("A new unit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitDTO result = unitService.save(unitDTO);
        return ResponseEntity.created(new URI("/api/units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /units : Updates an existing unit.
     *
     * @param unitDTO the unitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unitDTO,
     * or with status 400 (Bad Request) if the unitDTO is not valid,
     * or with status 500 (Internal Server Error) if the unitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/units")
    @Timed
    public ResponseEntity<UnitDTO> updateUnit(@Valid @RequestBody UnitDTO unitDTO) throws URISyntaxException {
        log.debug("REST request to update Unit : {}", unitDTO);
        if (unitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitDTO result = unitService.save(unitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /units : get all the units.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of units in body
     */
    @GetMapping("/units")
    @Timed
    public List<UnitDTO> getAllUnits() {
        log.debug("REST request to get all Units");
        return unitService.findAll();
    }

    /**
     * GET  /units/:id : get the "id" unit.
     *
     * @param id the id of the unitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/units/{id}")
    @Timed
    public ResponseEntity<UnitDTO> getUnit(@PathVariable Long id) {
        log.debug("REST request to get Unit : {}", id);
        Optional<UnitDTO> unitDTO = unitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitDTO);
    }

    /**
     * DELETE  /units/:id : delete the "id" unit.
     *
     * @param id the id of the unitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/units/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        log.debug("REST request to delete Unit : {}", id);
        unitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/units?query=:query : search for the unit corresponding
     * to the query.
     *
     * @param query the query of the unit search
     * @return the result of the search
     */
    @GetMapping("/_search/units")
    @Timed
    public List<UnitDTO> searchUnits(@RequestParam String query) {
        log.debug("REST request to search Units for query {}", query);
        return unitService.search(query);
    }

}
