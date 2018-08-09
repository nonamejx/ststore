package com.ststore.app.web.rest;

import com.ststore.app.StstoreApp;

import com.ststore.app.domain.PriceBook;
import com.ststore.app.repository.PriceBookRepository;
import com.ststore.app.repository.search.PriceBookSearchRepository;
import com.ststore.app.service.PriceBookService;
import com.ststore.app.service.dto.PriceBookDTO;
import com.ststore.app.service.mapper.PriceBookMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the PriceBookResource REST controller.
 *
 * @see PriceBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StstoreApp.class)
public class PriceBookResourceIntTest {

    private static final String DEFAULT_PRICE_BOOK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRICE_BOOK_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PriceBookRepository priceBookRepository;


    @Autowired
    private PriceBookMapper priceBookMapper;
    

    @Autowired
    private PriceBookService priceBookService;

    /**
     * This repository is mocked in the com.ststore.app.repository.search test package.
     *
     * @see com.ststore.app.repository.search.PriceBookSearchRepositoryMockConfiguration
     */
    @Autowired
    private PriceBookSearchRepository mockPriceBookSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriceBookMockMvc;

    private PriceBook priceBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceBookResource priceBookResource = new PriceBookResource(priceBookService);
        this.restPriceBookMockMvc = MockMvcBuilders.standaloneSetup(priceBookResource)
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
    public static PriceBook createEntity(EntityManager em) {
        PriceBook priceBook = new PriceBook()
            .priceBookName(DEFAULT_PRICE_BOOK_NAME)
            .createdDate(DEFAULT_CREATED_DATE);
        return priceBook;
    }

    @Before
    public void initTest() {
        priceBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceBook() throws Exception {
        int databaseSizeBeforeCreate = priceBookRepository.findAll().size();

        // Create the PriceBook
        PriceBookDTO priceBookDTO = priceBookMapper.toDto(priceBook);
        restPriceBookMockMvc.perform(post("/api/price-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceBook in the database
        List<PriceBook> priceBookList = priceBookRepository.findAll();
        assertThat(priceBookList).hasSize(databaseSizeBeforeCreate + 1);
        PriceBook testPriceBook = priceBookList.get(priceBookList.size() - 1);
        assertThat(testPriceBook.getPriceBookName()).isEqualTo(DEFAULT_PRICE_BOOK_NAME);
        assertThat(testPriceBook.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);

        // Validate the PriceBook in Elasticsearch
        verify(mockPriceBookSearchRepository, times(1)).save(testPriceBook);
    }

    @Test
    @Transactional
    public void createPriceBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceBookRepository.findAll().size();

        // Create the PriceBook with an existing ID
        priceBook.setId(1L);
        PriceBookDTO priceBookDTO = priceBookMapper.toDto(priceBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceBookMockMvc.perform(post("/api/price-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceBook in the database
        List<PriceBook> priceBookList = priceBookRepository.findAll();
        assertThat(priceBookList).hasSize(databaseSizeBeforeCreate);

        // Validate the PriceBook in Elasticsearch
        verify(mockPriceBookSearchRepository, times(0)).save(priceBook);
    }

    @Test
    @Transactional
    public void checkPriceBookNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceBookRepository.findAll().size();
        // set the field null
        priceBook.setPriceBookName(null);

        // Create the PriceBook, which fails.
        PriceBookDTO priceBookDTO = priceBookMapper.toDto(priceBook);

        restPriceBookMockMvc.perform(post("/api/price-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookDTO)))
            .andExpect(status().isBadRequest());

        List<PriceBook> priceBookList = priceBookRepository.findAll();
        assertThat(priceBookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceBookRepository.findAll().size();
        // set the field null
        priceBook.setCreatedDate(null);

        // Create the PriceBook, which fails.
        PriceBookDTO priceBookDTO = priceBookMapper.toDto(priceBook);

        restPriceBookMockMvc.perform(post("/api/price-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookDTO)))
            .andExpect(status().isBadRequest());

        List<PriceBook> priceBookList = priceBookRepository.findAll();
        assertThat(priceBookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceBooks() throws Exception {
        // Initialize the database
        priceBookRepository.saveAndFlush(priceBook);

        // Get all the priceBookList
        restPriceBookMockMvc.perform(get("/api/price-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].priceBookName").value(hasItem(DEFAULT_PRICE_BOOK_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getPriceBook() throws Exception {
        // Initialize the database
        priceBookRepository.saveAndFlush(priceBook);

        // Get the priceBook
        restPriceBookMockMvc.perform(get("/api/price-books/{id}", priceBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceBook.getId().intValue()))
            .andExpect(jsonPath("$.priceBookName").value(DEFAULT_PRICE_BOOK_NAME.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPriceBook() throws Exception {
        // Get the priceBook
        restPriceBookMockMvc.perform(get("/api/price-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceBook() throws Exception {
        // Initialize the database
        priceBookRepository.saveAndFlush(priceBook);

        int databaseSizeBeforeUpdate = priceBookRepository.findAll().size();

        // Update the priceBook
        PriceBook updatedPriceBook = priceBookRepository.findById(priceBook.getId()).get();
        // Disconnect from session so that the updates on updatedPriceBook are not directly saved in db
        em.detach(updatedPriceBook);
        updatedPriceBook
            .priceBookName(UPDATED_PRICE_BOOK_NAME)
            .createdDate(UPDATED_CREATED_DATE);
        PriceBookDTO priceBookDTO = priceBookMapper.toDto(updatedPriceBook);

        restPriceBookMockMvc.perform(put("/api/price-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookDTO)))
            .andExpect(status().isOk());

        // Validate the PriceBook in the database
        List<PriceBook> priceBookList = priceBookRepository.findAll();
        assertThat(priceBookList).hasSize(databaseSizeBeforeUpdate);
        PriceBook testPriceBook = priceBookList.get(priceBookList.size() - 1);
        assertThat(testPriceBook.getPriceBookName()).isEqualTo(UPDATED_PRICE_BOOK_NAME);
        assertThat(testPriceBook.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);

        // Validate the PriceBook in Elasticsearch
        verify(mockPriceBookSearchRepository, times(1)).save(testPriceBook);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceBook() throws Exception {
        int databaseSizeBeforeUpdate = priceBookRepository.findAll().size();

        // Create the PriceBook
        PriceBookDTO priceBookDTO = priceBookMapper.toDto(priceBook);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPriceBookMockMvc.perform(put("/api/price-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceBook in the database
        List<PriceBook> priceBookList = priceBookRepository.findAll();
        assertThat(priceBookList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PriceBook in Elasticsearch
        verify(mockPriceBookSearchRepository, times(0)).save(priceBook);
    }

    @Test
    @Transactional
    public void deletePriceBook() throws Exception {
        // Initialize the database
        priceBookRepository.saveAndFlush(priceBook);

        int databaseSizeBeforeDelete = priceBookRepository.findAll().size();

        // Get the priceBook
        restPriceBookMockMvc.perform(delete("/api/price-books/{id}", priceBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceBook> priceBookList = priceBookRepository.findAll();
        assertThat(priceBookList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PriceBook in Elasticsearch
        verify(mockPriceBookSearchRepository, times(1)).deleteById(priceBook.getId());
    }

    @Test
    @Transactional
    public void searchPriceBook() throws Exception {
        // Initialize the database
        priceBookRepository.saveAndFlush(priceBook);
        when(mockPriceBookSearchRepository.search(queryStringQuery("id:" + priceBook.getId())))
            .thenReturn(Collections.singletonList(priceBook));
        // Search the priceBook
        restPriceBookMockMvc.perform(get("/api/_search/price-books?query=id:" + priceBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].priceBookName").value(hasItem(DEFAULT_PRICE_BOOK_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceBook.class);
        PriceBook priceBook1 = new PriceBook();
        priceBook1.setId(1L);
        PriceBook priceBook2 = new PriceBook();
        priceBook2.setId(priceBook1.getId());
        assertThat(priceBook1).isEqualTo(priceBook2);
        priceBook2.setId(2L);
        assertThat(priceBook1).isNotEqualTo(priceBook2);
        priceBook1.setId(null);
        assertThat(priceBook1).isNotEqualTo(priceBook2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceBookDTO.class);
        PriceBookDTO priceBookDTO1 = new PriceBookDTO();
        priceBookDTO1.setId(1L);
        PriceBookDTO priceBookDTO2 = new PriceBookDTO();
        assertThat(priceBookDTO1).isNotEqualTo(priceBookDTO2);
        priceBookDTO2.setId(priceBookDTO1.getId());
        assertThat(priceBookDTO1).isEqualTo(priceBookDTO2);
        priceBookDTO2.setId(2L);
        assertThat(priceBookDTO1).isNotEqualTo(priceBookDTO2);
        priceBookDTO1.setId(null);
        assertThat(priceBookDTO1).isNotEqualTo(priceBookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priceBookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priceBookMapper.fromId(null)).isNull();
    }
}
