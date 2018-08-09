package com.ststore.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PriceBookSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PriceBookSearchRepositoryMockConfiguration {

    @MockBean
    private PriceBookSearchRepository mockPriceBookSearchRepository;

}
