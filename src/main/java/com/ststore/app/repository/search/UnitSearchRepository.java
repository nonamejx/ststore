package com.ststore.app.repository.search;

import com.ststore.app.domain.Unit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Unit entity.
 */
public interface UnitSearchRepository extends ElasticsearchRepository<Unit, Long> {
}
