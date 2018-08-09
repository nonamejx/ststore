package com.ststore.app.service;

import com.ststore.app.service.dto.PriceBookDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PriceBook.
 */
public interface PriceBookService {

    /**
     * Save a priceBook.
     *
     * @param priceBookDTO the entity to save
     * @return the persisted entity
     */
    PriceBookDTO save(PriceBookDTO priceBookDTO);

    /**
     * Get all the priceBooks.
     *
     * @return the list of entities
     */
    List<PriceBookDTO> findAll();


    /**
     * Get the "id" priceBook.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PriceBookDTO> findOne(Long id);

    /**
     * Delete the "id" priceBook.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the priceBook corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PriceBookDTO> search(String query);
}
