package com.ststore.app.repository.search;

import com.ststore.app.domain.PriceBookItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PriceBookItem entity.
 */
public interface PriceBookItemSearchRepository extends ElasticsearchRepository<PriceBookItem, Long> {
}
