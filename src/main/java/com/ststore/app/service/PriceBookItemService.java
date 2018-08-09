package com.ststore.app.service;

import com.ststore.app.service.dto.PriceBookItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PriceBookItem.
 */
public interface PriceBookItemService {

    /**
     * Save a priceBookItem.
     *
     * @param priceBookItemDTO the entity to save
     * @return the persisted entity
     */
    PriceBookItemDTO save(PriceBookItemDTO priceBookItemDTO);

    /**
     * Get all the priceBookItems.
     *
     * @return the list of entities
     */
    List<PriceBookItemDTO> findAll();


    /**
     * Get the "id" priceBookItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PriceBookItemDTO> findOne(Long id);

    /**
     * Delete the "id" priceBookItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the priceBookItem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PriceBookItemDTO> search(String query);
}
