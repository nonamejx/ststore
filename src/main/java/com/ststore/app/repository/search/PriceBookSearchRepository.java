package com.ststore.app.repository.search;

import com.ststore.app.domain.PriceBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PriceBook entity.
 */
public interface PriceBookSearchRepository extends ElasticsearchRepository<PriceBook, Long> {
}
