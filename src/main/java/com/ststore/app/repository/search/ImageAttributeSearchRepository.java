package com.ststore.app.repository.search;

import com.ststore.app.domain.ImageAttribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ImageAttribute entity.
 */
public interface ImageAttributeSearchRepository extends ElasticsearchRepository<ImageAttribute, Long> {
}
