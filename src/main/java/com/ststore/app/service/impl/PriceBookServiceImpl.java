package com.ststore.app.service.impl;

import com.ststore.app.service.PriceBookService;
import com.ststore.app.domain.PriceBook;
import com.ststore.app.repository.PriceBookRepository;
import com.ststore.app.repository.search.PriceBookSearchRepository;
import com.ststore.app.service.dto.PriceBookDTO;
import com.ststore.app.service.mapper.PriceBookMapper;
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
 * Service Implementation for managing PriceBook.
 */
@Service
@Transactional
public class PriceBookServiceImpl implements PriceBookService {

    private final Logger log = LoggerFactory.getLogger(PriceBookServiceImpl.class);

    private final PriceBookRepository priceBookRepository;

    private final PriceBookMapper priceBookMapper;

    private final PriceBookSearchRepository priceBookSearchRepository;

    public PriceBookServiceImpl(PriceBookRepository priceBookRepository, PriceBookMapper priceBookMapper, PriceBookSearchRepository priceBookSearchRepository) {
        this.priceBookRepository = priceBookRepository;
        this.priceBookMapper = priceBookMapper;
        this.priceBookSearchRepository = priceBookSearchRepository;
    }

    /**
     * Save a priceBook.
     *
     * @param priceBookDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PriceBookDTO save(PriceBookDTO priceBookDTO) {
        log.debug("Request to save PriceBook : {}", priceBookDTO);
        PriceBook priceBook = priceBookMapper.toEntity(priceBookDTO);
        priceBook = priceBookRepository.save(priceBook);
        PriceBookDTO result = priceBookMapper.toDto(priceBook);
        priceBookSearchRepository.save(priceBook);
        return result;
    }

    /**
     * Get all the priceBooks.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriceBookDTO> findAll() {
        log.debug("Request to get all PriceBooks");
        return priceBookRepository.findAll().stream()
            .map(priceBookMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one priceBook by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriceBookDTO> findOne(Long id) {
        log.debug("Request to get PriceBook : {}", id);
        return priceBookRepository.findById(id)
            .map(priceBookMapper::toDto);
    }

    /**
     * Delete the priceBook by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceBook : {}", id);
        priceBookRepository.deleteById(id);
        priceBookSearchRepository.deleteById(id);
    }

    /**
     * Search for the priceBook corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriceBookDTO> search(String query) {
        log.debug("Request to search PriceBooks for query {}", query);
        return StreamSupport
            .stream(priceBookSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(priceBookMapper::toDto)
            .collect(Collectors.toList());
    }
}
