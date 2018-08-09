package com.ststore.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PriceBookItemSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PriceBookItemSearchRepositoryMockConfiguration {

    @MockBean
    private PriceBookItemSearchRepository mockPriceBookItemSearchRepository;

}
