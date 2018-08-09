package com.ststore.app.service;

import com.ststore.app.service.dto.ImageAttributeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ImageAttribute.
 */
public interface ImageAttributeService {

    /**
     * Save a imageAttribute.
     *
     * @param imageAttributeDTO the entity to save
     * @return the persisted entity
     */
    ImageAttributeDTO save(ImageAttributeDTO imageAttributeDTO);

    /**
     * Get all the imageAttributes.
     *
     * @return the list of entities
     */
    List<ImageAttributeDTO> findAll();


    /**
     * Get the "id" imageAttribute.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ImageAttributeDTO> findOne(Long id);

    /**
     * Delete the "id" imageAttribute.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the imageAttribute corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ImageAttributeDTO> search(String query);
}
