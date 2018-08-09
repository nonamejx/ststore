package com.ststore.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ImageAttributeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ImageAttributeSearchRepositoryMockConfiguration {

    @MockBean
    private ImageAttributeSearchRepository mockImageAttributeSearchRepository;

}
