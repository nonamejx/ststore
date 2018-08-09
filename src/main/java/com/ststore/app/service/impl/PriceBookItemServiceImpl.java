package com.ststore.app.service.impl;

import com.ststore.app.service.PriceBookItemService;
import com.ststore.app.domain.PriceBookItem;
import com.ststore.app.repository.PriceBookItemRepository;
import com.ststore.app.repository.search.PriceBookItemSearchRepository;
import com.ststore.app.service.dto.PriceBookItemDTO;
import com.ststore.app.service.mapper.PriceBookItemMapper;
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
 * Service Implementation for managing PriceBookItem.
 */
@Service
@Transactional
public class PriceBookItemServiceImpl implements PriceBookItemService {

    private final Logger log = LoggerFactory.getLogger(PriceBookItemServiceImpl.class);

    private final PriceBookItemRepository priceBookItemRepository;

    private final PriceBookItemMapper priceBookItemMapper;

    private final PriceBookItemSearchRepository priceBookItemSearchRepository;

    public PriceBookItemServiceImpl(PriceBookItemRepository priceBookItemRepository, PriceBookItemMapper priceBookItemMapper, PriceBookItemSearchRepository priceBookItemSearchRepository) {
        this.priceBookItemRepository = priceBookItemRepository;
        this.priceBookItemMapper = priceBookItemMapper;
        this.priceBookItemSearchRepository = priceBookItemSearchRepository;
    }

    /**
     * Save a priceBookItem.
     *
     * @param priceBookItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PriceBookItemDTO save(PriceBookItemDTO priceBookItemDTO) {
        log.debug("Request to save PriceBookItem : {}", priceBookItemDTO);
        PriceBookItem priceBookItem = priceBookItemMapper.toEntity(priceBookItemDTO);
        priceBookItem = priceBookItemRepository.save(priceBookItem);
        PriceBookItemDTO result = priceBookItemMapper.toDto(priceBookItem);
        priceBookItemSearchRepository.save(priceBookItem);
        return result;
    }

    /**
     * Get all the priceBookItems.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriceBookItemDTO> findAll() {
        log.debug("Request to get all PriceBookItems");
        return priceBookItemRepository.findAll().stream()
            .map(priceBookItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one priceBookItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriceBookItemDTO> findOne(Long id) {
        log.debug("Request to get PriceBookItem : {}", id);
        return priceBookItemRepository.findById(id)
            .map(priceBookItemMapper::toDto);
    }

    /**
     * Delete the priceBookItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceBookItem : {}", id);
        priceBookItemRepository.deleteById(id);
        priceBookItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the priceBookItem corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriceBookItemDTO> search(String query) {
        log.debug("Request to search PriceBookItems for query {}", query);
        return StreamSupport
            .stream(priceBookItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(priceBookItemMapper::toDto)
            .collect(Collectors.toList());
    }
}
