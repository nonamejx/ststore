package com.ststore.app.service.impl;

import com.ststore.app.service.ImageAttributeService;
import com.ststore.app.domain.ImageAttribute;
import com.ststore.app.repository.ImageAttributeRepository;
import com.ststore.app.repository.search.ImageAttributeSearchRepository;
import com.ststore.app.service.dto.ImageAttributeDTO;
import com.ststore.app.service.mapper.ImageAttributeMapper;
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
 * Service Implementation for managing ImageAttribute.
 */
@Service
@Transactional
public class ImageAttributeServiceImpl implements ImageAttributeService {

    private final Logger log = LoggerFactory.getLogger(ImageAttributeServiceImpl.class);

    private final ImageAttributeRepository imageAttributeRepository;

    private final ImageAttributeMapper imageAttributeMapper;

    private final ImageAttributeSearchRepository imageAttributeSearchRepository;

    public ImageAttributeServiceImpl(ImageAttributeRepository imageAttributeRepository, ImageAttributeMapper imageAttributeMapper, ImageAttributeSearchRepository imageAttributeSearchRepository) {
        this.imageAttributeRepository = imageAttributeRepository;
        this.imageAttributeMapper = imageAttributeMapper;
        this.imageAttributeSearchRepository = imageAttributeSearchRepository;
    }

    /**
     * Save a imageAttribute.
     *
     * @param imageAttributeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ImageAttributeDTO save(ImageAttributeDTO imageAttributeDTO) {
        log.debug("Request to save ImageAttribute : {}", imageAttributeDTO);
        ImageAttribute imageAttribute = imageAttributeMapper.toEntity(imageAttributeDTO);
        imageAttribute = imageAttributeRepository.save(imageAttribute);
        ImageAttributeDTO result = imageAttributeMapper.toDto(imageAttribute);
        imageAttributeSearchRepository.save(imageAttribute);
        return result;
    }

    /**
     * Get all the imageAttributes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImageAttributeDTO> findAll() {
        log.debug("Request to get all ImageAttributes");
        return imageAttributeRepository.findAll().stream()
            .map(imageAttributeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one imageAttribute by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImageAttributeDTO> findOne(Long id) {
        log.debug("Request to get ImageAttribute : {}", id);
        return imageAttributeRepository.findById(id)
            .map(imageAttributeMapper::toDto);
    }

    /**
     * Delete the imageAttribute by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImageAttribute : {}", id);
        imageAttributeRepository.deleteById(id);
        imageAttributeSearchRepository.deleteById(id);
    }

    /**
     * Search for the imageAttribute corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImageAttributeDTO> search(String query) {
        log.debug("Request to search ImageAttributes for query {}", query);
        return StreamSupport
            .stream(imageAttributeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(imageAttributeMapper::toDto)
            .collect(Collectors.toList());
    }
}
