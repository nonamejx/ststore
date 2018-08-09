package com.ststore.app.web.rest;

import com.ststore.app.StstoreApp;

import com.ststore.app.domain.ImageAttribute;
import com.ststore.app.repository.ImageAttributeRepository;
import com.ststore.app.repository.search.ImageAttributeSearchRepository;
import com.ststore.app.service.ImageAttributeService;
import com.ststore.app.service.dto.ImageAttributeDTO;
import com.ststore.app.service.mapper.ImageAttributeMapper;
import com.ststore.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.ststore.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImageAttributeResource REST controller.
 *
 * @see ImageAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StstoreApp.class)
public class ImageAttributeResourceIntTest {

    private static final String DEFAULT_IMAGE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_LINK = "BBBBBBBBBB";

    @Autowired
    private ImageAttributeRepository imageAttributeRepository;


    @Autowired
    private ImageAttributeMapper imageAttributeMapper;
    

    @Autowired
    private ImageAttributeService imageAttributeService;

    /**
     * This repository is mocked in the com.ststore.app.repository.search test package.
     *
     * @see com.ststore.app.repository.search.ImageAttributeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ImageAttributeSearchRepository mockImageAttributeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImageAttributeMockMvc;

    private ImageAttribute imageAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageAttributeResource imageAttributeResource = new ImageAttributeResource(imageAttributeService);
        this.restImageAttributeMockMvc = MockMvcBuilders.standaloneSetup(imageAttributeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageAttribute createEntity(EntityManager em) {
        ImageAttribute imageAttribute = new ImageAttribute()
            .imageLink(DEFAULT_IMAGE_LINK);
        return imageAttribute;
    }

    @Before
    public void initTest() {
        imageAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createImageAttribute() throws Exception {
        int databaseSizeBeforeCreate = imageAttributeRepository.findAll().size();

        // Create the ImageAttribute
        ImageAttributeDTO imageAttributeDTO = imageAttributeMapper.toDto(imageAttribute);
        restImageAttributeMockMvc.perform(post("/api/image-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageAttributeDTO)))
            .andExpect(status().isCreated());

        // Validate the ImageAttribute in the database
        List<ImageAttribute> imageAttributeList = imageAttributeRepository.findAll();
        assertThat(imageAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ImageAttribute testImageAttribute = imageAttributeList.get(imageAttributeList.size() - 1);
        assertThat(testImageAttribute.getImageLink()).isEqualTo(DEFAULT_IMAGE_LINK);

        // Validate the ImageAttribute in Elasticsearch
        verify(mockImageAttributeSearchRepository, times(1)).save(testImageAttribute);
    }

    @Test
    @Transactional
    public void createImageAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageAttributeRepository.findAll().size();

        // Create the ImageAttribute with an existing ID
        imageAttribute.setId(1L);
        ImageAttributeDTO imageAttributeDTO = imageAttributeMapper.toDto(imageAttribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageAttributeMockMvc.perform(post("/api/image-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImageAttribute in the database
        List<ImageAttribute> imageAttributeList = imageAttributeRepository.findAll();
        assertThat(imageAttributeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ImageAttribute in Elasticsearch
        verify(mockImageAttributeSearchRepository, times(0)).save(imageAttribute);
    }

    @Test
    @Transactional
    public void checkImageLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageAttributeRepository.findAll().size();
        // set the field null
        imageAttribute.setImageLink(null);

        // Create the ImageAttribute, which fails.
        ImageAttributeDTO imageAttributeDTO = imageAttributeMapper.toDto(imageAttribute);

        restImageAttributeMockMvc.perform(post("/api/image-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageAttributeDTO)))
            .andExpect(status().isBadRequest());

        List<ImageAttribute> imageAttributeList = imageAttributeRepository.findAll();
        assertThat(imageAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImageAttributes() throws Exception {
        // Initialize the database
        imageAttributeRepository.saveAndFlush(imageAttribute);

        // Get all the imageAttributeList
        restImageAttributeMockMvc.perform(get("/api/image-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageLink").value(hasItem(DEFAULT_IMAGE_LINK.toString())));
    }
    

    @Test
    @Transactional
    public void getImageAttribute() throws Exception {
        // Initialize the database
        imageAttributeRepository.saveAndFlush(imageAttribute);

        // Get the imageAttribute
        restImageAttributeMockMvc.perform(get("/api/image-attributes/{id}", imageAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imageAttribute.getId().intValue()))
            .andExpect(jsonPath("$.imageLink").value(DEFAULT_IMAGE_LINK.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingImageAttribute() throws Exception {
        // Get the imageAttribute
        restImageAttributeMockMvc.perform(get("/api/image-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageAttribute() throws Exception {
        // Initialize the database
        imageAttributeRepository.saveAndFlush(imageAttribute);

        int databaseSizeBeforeUpdate = imageAttributeRepository.findAll().size();

        // Update the imageAttribute
        ImageAttribute updatedImageAttribute = imageAttributeRepository.findById(imageAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedImageAttribute are not directly saved in db
        em.detach(updatedImageAttribute);
        updatedImageAttribute
            .imageLink(UPDATED_IMAGE_LINK);
        ImageAttributeDTO imageAttributeDTO = imageAttributeMapper.toDto(updatedImageAttribute);

        restImageAttributeMockMvc.perform(put("/api/image-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageAttributeDTO)))
            .andExpect(status().isOk());

        // Validate the ImageAttribute in the database
        List<ImageAttribute> imageAttributeList = imageAttributeRepository.findAll();
        assertThat(imageAttributeList).hasSize(databaseSizeBeforeUpdate);
        ImageAttribute testImageAttribute = imageAttributeList.get(imageAttributeList.size() - 1);
        assertThat(testImageAttribute.getImageLink()).isEqualTo(UPDATED_IMAGE_LINK);

        // Validate the ImageAttribute in Elasticsearch
        verify(mockImageAttributeSearchRepository, times(1)).save(testImageAttribute);
    }

    @Test
    @Transactional
    public void updateNonExistingImageAttribute() throws Exception {
        int databaseSizeBeforeUpdate = imageAttributeRepository.findAll().size();

        // Create the ImageAttribute
        ImageAttributeDTO imageAttributeDTO = imageAttributeMapper.toDto(imageAttribute);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImageAttributeMockMvc.perform(put("/api/image-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImageAttribute in the database
        List<ImageAttribute> imageAttributeList = imageAttributeRepository.findAll();
        assertThat(imageAttributeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ImageAttribute in Elasticsearch
        verify(mockImageAttributeSearchRepository, times(0)).save(imageAttribute);
    }

    @Test
    @Transactional
    public void deleteImageAttribute() throws Exception {
        // Initialize the database
        imageAttributeRepository.saveAndFlush(imageAttribute);

        int databaseSizeBeforeDelete = imageAttributeRepository.findAll().size();

        // Get the imageAttribute
        restImageAttributeMockMvc.perform(delete("/api/image-attributes/{id}", imageAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ImageAttribute> imageAttributeList = imageAttributeRepository.findAll();
        assertThat(imageAttributeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ImageAttribute in Elasticsearch
        verify(mockImageAttributeSearchRepository, times(1)).deleteById(imageAttribute.getId());
    }

    @Test
    @Transactional
    public void searchImageAttribute() throws Exception {
        // Initialize the database
        imageAttributeRepository.saveAndFlush(imageAttribute);
        when(mockImageAttributeSearchRepository.search(queryStringQuery("id:" + imageAttribute.getId())))
            .thenReturn(Collections.singletonList(imageAttribute));
        // Search the imageAttribute
        restImageAttributeMockMvc.perform(get("/api/_search/image-attributes?query=id:" + imageAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageLink").value(hasItem(DEFAULT_IMAGE_LINK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageAttribute.class);
        ImageAttribute imageAttribute1 = new ImageAttribute();
        imageAttribute1.setId(1L);
        ImageAttribute imageAttribute2 = new ImageAttribute();
        imageAttribute2.setId(imageAttribute1.getId());
        assertThat(imageAttribute1).isEqualTo(imageAttribute2);
        imageAttribute2.setId(2L);
        assertThat(imageAttribute1).isNotEqualTo(imageAttribute2);
        imageAttribute1.setId(null);
        assertThat(imageAttribute1).isNotEqualTo(imageAttribute2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageAttributeDTO.class);
        ImageAttributeDTO imageAttributeDTO1 = new ImageAttributeDTO();
        imageAttributeDTO1.setId(1L);
        ImageAttributeDTO imageAttributeDTO2 = new ImageAttributeDTO();
        assertThat(imageAttributeDTO1).isNotEqualTo(imageAttributeDTO2);
        imageAttributeDTO2.setId(imageAttributeDTO1.getId());
        assertThat(imageAttributeDTO1).isEqualTo(imageAttributeDTO2);
        imageAttributeDTO2.setId(2L);
        assertThat(imageAttributeDTO1).isNotEqualTo(imageAttributeDTO2);
        imageAttributeDTO1.setId(null);
        assertThat(imageAttributeDTO1).isNotEqualTo(imageAttributeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(imageAttributeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(imageAttributeMapper.fromId(null)).isNull();
    }
}
