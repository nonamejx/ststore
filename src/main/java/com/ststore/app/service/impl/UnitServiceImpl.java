package com.ststore.app.service.impl;

import com.ststore.app.service.UnitService;
import com.ststore.app.domain.Unit;
import com.ststore.app.repository.UnitRepository;
import com.ststore.app.repository.search.UnitSearchRepository;
import com.ststore.app.service.dto.UnitDTO;
import com.ststore.app.service.mapper.UnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Unit.
 */
@Service
@Transactional
public class UnitServiceImpl implements UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitServiceImpl.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    private final UnitSearchRepository unitSearchRepository;

    public UnitServiceImpl(UnitRepository unitRepository, UnitMapper unitMapper, UnitSearchRepository unitSearchRepository) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
        this.unitSearchRepository = unitSearchRepository;
    }

    /**
     * Save a unit.
     *
     * @param unitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UnitDTO save(UnitDTO unitDTO) {
        log.debug("Request to save Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        unit = unitRepository.save(unit);
        UnitDTO result = unitMapper.toDto(unit);
        unitSearchRepository.save(unit);
        return result;
    }

    /**
     * Get all the units.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UnitDTO> findAll() {
        log.debug("Request to get all Units");
        return unitRepository.findAll().stream()
            .map(unitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one unit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnitDTO> findOne(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id)
            .map(unitMapper::toDto);
    }

    /**
     * Delete the unit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
        unitSearchRepository.deleteById(id);
    }

    /**
     * Search for the unit corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UnitDTO> search(String query) {
        log.debug("Request to search Units for query {}", query);
        return StreamSupport
            .stream(unitSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(unitMapper::toDto)
            .collect(Collectors.toList());
    }
}
