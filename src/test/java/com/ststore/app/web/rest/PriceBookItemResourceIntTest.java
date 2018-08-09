package com.ststore.app.web.rest;

import com.ststore.app.StstoreApp;

import com.ststore.app.domain.PriceBookItem;
import com.ststore.app.repository.PriceBookItemRepository;
import com.ststore.app.repository.search.PriceBookItemSearchRepository;
import com.ststore.app.service.PriceBookItemService;
import com.ststore.app.service.dto.PriceBookItemDTO;
import com.ststore.app.service.mapper.PriceBookItemMapper;
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
import java.math.BigDecimal;
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
 * Test class for the PriceBookItemResource REST controller.
 *
 * @see PriceBookItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StstoreApp.class)
public class PriceBookItemResourceIntTest {

    private static final BigDecimal DEFAULT_NEW_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NEW_PRICE = new BigDecimal(2);

    @Autowired
    private PriceBookItemRepository priceBookItemRepository;


    @Autowired
    private PriceBookItemMapper priceBookItemMapper;
    

    @Autowired
    private PriceBookItemService priceBookItemService;

    /**
     * This repository is mocked in the com.ststore.app.repository.search test package.
     *
     * @see com.ststore.app.repository.search.PriceBookItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private PriceBookItemSearchRepository mockPriceBookItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriceBookItemMockMvc;

    private PriceBookItem priceBookItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceBookItemResource priceBookItemResource = new PriceBookItemResource(priceBookItemService);
        this.restPriceBookItemMockMvc = MockMvcBuilders.standaloneSetup(priceBookItemResource)
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
    public static PriceBookItem createEntity(EntityManager em) {
        PriceBookItem priceBookItem = new PriceBookItem()
            .newPrice(DEFAULT_NEW_PRICE);
        return priceBookItem;
    }

    @Before
    public void initTest() {
        priceBookItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceBookItem() throws Exception {
        int databaseSizeBeforeCreate = priceBookItemRepository.findAll().size();

        // Create the PriceBookItem
        PriceBookItemDTO priceBookItemDTO = priceBookItemMapper.toDto(priceBookItem);
        restPriceBookItemMockMvc.perform(post("/api/price-book-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceBookItem in the database
        List<PriceBookItem> priceBookItemList = priceBookItemRepository.findAll();
        assertThat(priceBookItemList).hasSize(databaseSizeBeforeCreate + 1);
        PriceBookItem testPriceBookItem = priceBookItemList.get(priceBookItemList.size() - 1);
        assertThat(testPriceBookItem.getNewPrice()).isEqualTo(DEFAULT_NEW_PRICE);

        // Validate the PriceBookItem in Elasticsearch
        verify(mockPriceBookItemSearchRepository, times(1)).save(testPriceBookItem);
    }

    @Test
    @Transactional
    public void createPriceBookItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceBookItemRepository.findAll().size();

        // Create the PriceBookItem with an existing ID
        priceBookItem.setId(1L);
        PriceBookItemDTO priceBookItemDTO = priceBookItemMapper.toDto(priceBookItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceBookItemMockMvc.perform(post("/api/price-book-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceBookItem in the database
        List<PriceBookItem> priceBookItemList = priceBookItemRepository.findAll();
        assertThat(priceBookItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the PriceBookItem in Elasticsearch
        verify(mockPriceBookItemSearchRepository, times(0)).save(priceBookItem);
    }

    @Test
    @Transactional
    public void checkNewPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceBookItemRepository.findAll().size();
        // set the field null
        priceBookItem.setNewPrice(null);

        // Create the PriceBookItem, which fails.
        PriceBookItemDTO priceBookItemDTO = priceBookItemMapper.toDto(priceBookItem);

        restPriceBookItemMockMvc.perform(post("/api/price-book-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookItemDTO)))
            .andExpect(status().isBadRequest());

        List<PriceBookItem> priceBookItemList = priceBookItemRepository.findAll();
        assertThat(priceBookItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceBookItems() throws Exception {
        // Initialize the database
        priceBookItemRepository.saveAndFlush(priceBookItem);

        // Get all the priceBookItemList
        restPriceBookItemMockMvc.perform(get("/api/price-book-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceBookItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].newPrice").value(hasItem(DEFAULT_NEW_PRICE.intValue())));
    }
    

    @Test
    @Transactional
    public void getPriceBookItem() throws Exception {
        // Initialize the database
        priceBookItemRepository.saveAndFlush(priceBookItem);

        // Get the priceBookItem
        restPriceBookItemMockMvc.perform(get("/api/price-book-items/{id}", priceBookItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceBookItem.getId().intValue()))
            .andExpect(jsonPath("$.newPrice").value(DEFAULT_NEW_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPriceBookItem() throws Exception {
        // Get the priceBookItem
        restPriceBookItemMockMvc.perform(get("/api/price-book-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceBookItem() throws Exception {
        // Initialize the database
        priceBookItemRepository.saveAndFlush(priceBookItem);

        int databaseSizeBeforeUpdate = priceBookItemRepository.findAll().size();

        // Update the priceBookItem
        PriceBookItem updatedPriceBookItem = priceBookItemRepository.findById(priceBookItem.getId()).get();
        // Disconnect from session so that the updates on updatedPriceBookItem are not directly saved in db
        em.detach(updatedPriceBookItem);
        updatedPriceBookItem
            .newPrice(UPDATED_NEW_PRICE);
        PriceBookItemDTO priceBookItemDTO = priceBookItemMapper.toDto(updatedPriceBookItem);

        restPriceBookItemMockMvc.perform(put("/api/price-book-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookItemDTO)))
            .andExpect(status().isOk());

        // Validate the PriceBookItem in the database
        List<PriceBookItem> priceBookItemList = priceBookItemRepository.findAll();
        assertThat(priceBookItemList).hasSize(databaseSizeBeforeUpdate);
        PriceBookItem testPriceBookItem = priceBookItemList.get(priceBookItemList.size() - 1);
        assertThat(testPriceBookItem.getNewPrice()).isEqualTo(UPDATED_NEW_PRICE);

        // Validate the PriceBookItem in Elasticsearch
        verify(mockPriceBookItemSearchRepository, times(1)).save(testPriceBookItem);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceBookItem() throws Exception {
        int databaseSizeBeforeUpdate = priceBookItemRepository.findAll().size();

        // Create the PriceBookItem
        PriceBookItemDTO priceBookItemDTO = priceBookItemMapper.toDto(priceBookItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPriceBookItemMockMvc.perform(put("/api/price-book-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceBookItem in the database
        List<PriceBookItem> priceBookItemList = priceBookItemRepository.findAll();
        assertThat(priceBookItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PriceBookItem in Elasticsearch
        verify(mockPriceBookItemSearchRepository, times(0)).save(priceBookItem);
    }

    @Test
    @Transactional
    public void deletePriceBookItem() throws Exception {
        // Initialize the database
        priceBookItemRepository.saveAndFlush(priceBookItem);

        int databaseSizeBeforeDelete = priceBookItemRepository.findAll().size();

        // Get the priceBookItem
        restPriceBookItemMockMvc.perform(delete("/api/price-book-items/{id}", priceBookItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceBookItem> priceBookItemList = priceBookItemRepository.findAll();
        assertThat(priceBookItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PriceBookItem in Elasticsearch
        verify(mockPriceBookItemSearchRepository, times(1)).deleteById(priceBookItem.getId());
    }

    @Test
    @Transactional
    public void searchPriceBookItem() throws Exception {
        // Initialize the database
        priceBookItemRepository.saveAndFlush(priceBookItem);
        when(mockPriceBookItemSearchRepository.search(queryStringQuery("id:" + priceBookItem.getId())))
            .thenReturn(Collections.singletonList(priceBookItem));
        // Search the priceBookItem
        restPriceBookItemMockMvc.perform(get("/api/_search/price-book-items?query=id:" + priceBookItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceBookItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].newPrice").value(hasItem(DEFAULT_NEW_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceBookItem.class);
        PriceBookItem priceBookItem1 = new PriceBookItem();
        priceBookItem1.setId(1L);
        PriceBookItem priceBookItem2 = new PriceBookItem();
        priceBookItem2.setId(priceBookItem1.getId());
        assertThat(priceBookItem1).isEqualTo(priceBookItem2);
        priceBookItem2.setId(2L);
        assertThat(priceBookItem1).isNotEqualTo(priceBookItem2);
        priceBookItem1.setId(null);
        assertThat(priceBookItem1).isNotEqualTo(priceBookItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceBookItemDTO.class);
        PriceBookItemDTO priceBookItemDTO1 = new PriceBookItemDTO();
        priceBookItemDTO1.setId(1L);
        PriceBookItemDTO priceBookItemDTO2 = new PriceBookItemDTO();
        assertThat(priceBookItemDTO1).isNotEqualTo(priceBookItemDTO2);
        priceBookItemDTO2.setId(priceBookItemDTO1.getId());
        assertThat(priceBookItemDTO1).isEqualTo(priceBookItemDTO2);
        priceBookItemDTO2.setId(2L);
        assertThat(priceBookItemDTO1).isNotEqualTo(priceBookItemDTO2);
        priceBookItemDTO1.setId(null);
        assertThat(priceBookItemDTO1).isNotEqualTo(priceBookItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priceBookItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priceBookItemMapper.fromId(null)).isNull();
    }
}
