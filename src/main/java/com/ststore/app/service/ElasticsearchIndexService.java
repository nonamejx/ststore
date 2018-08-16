package com.ststore.app.service;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ststore.app.domain.*;
import com.ststore.app.repository.*;
import com.ststore.app.repository.search.*;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToMany;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ElasticsearchIndexService {

    private static final Lock reindexLock = new ReentrantLock();

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final CategoryRepository categoryRepository;

    private final CategorySearchRepository categorySearchRepository;

    private final ImageAttributeRepository imageAttributeRepository;

    private final ImageAttributeSearchRepository imageAttributeSearchRepository;

    private final PriceBookRepository priceBookRepository;

    private final PriceBookSearchRepository priceBookSearchRepository;

    private final PriceBookItemRepository priceBookItemRepository;

    private final PriceBookItemSearchRepository priceBookItemSearchRepository;

    private final ProductRepository productRepository;

    private final ProductSearchRepository productSearchRepository;

    private final UnitRepository unitRepository;

    private final UnitSearchRepository unitSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        CategoryRepository categoryRepository,
        CategorySearchRepository categorySearchRepository,
        ImageAttributeRepository imageAttributeRepository,
        ImageAttributeSearchRepository imageAttributeSearchRepository,
        PriceBookRepository priceBookRepository,
        PriceBookSearchRepository priceBookSearchRepository,
        PriceBookItemRepository priceBookItemRepository,
        PriceBookItemSearchRepository priceBookItemSearchRepository,
        ProductRepository productRepository,
        ProductSearchRepository productSearchRepository,
        UnitRepository unitRepository,
        UnitSearchRepository unitSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.categoryRepository = categoryRepository;
        this.categorySearchRepository = categorySearchRepository;
        this.imageAttributeRepository = imageAttributeRepository;
        this.imageAttributeSearchRepository = imageAttributeSearchRepository;
        this.priceBookRepository = priceBookRepository;
        this.priceBookSearchRepository = priceBookSearchRepository;
        this.priceBookItemRepository = priceBookItemRepository;
        this.priceBookItemSearchRepository = priceBookItemSearchRepository;
        this.productRepository = productRepository;
        this.productSearchRepository = productSearchRepository;
        this.unitRepository = unitRepository;
        this.unitSearchRepository = unitSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        if (reindexLock.tryLock()) {
            try {
                reindexForClass(Category.class, categoryRepository, categorySearchRepository);
                reindexForClass(ImageAttribute.class, imageAttributeRepository, imageAttributeSearchRepository);
                reindexForClass(PriceBook.class, priceBookRepository, priceBookSearchRepository);
                reindexForClass(PriceBookItem.class, priceBookItemRepository, priceBookItemSearchRepository);
                reindexForClass(Product.class, productRepository, productSearchRepository);
                reindexForClass(Unit.class, unitRepository, unitSearchRepository);
                reindexForClass(User.class, userRepository, userSearchRepository);

                log.info("Elasticsearch: Successfully performed reindexing");
            } finally {
                reindexLock.unlock();
            }
        } else {
            log.info("Elasticsearch: concurrent reindexing attempt");
        }
    }

    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (ResourceAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            // if a JHipster entity field is the owner side of a many-to-many relationship, it should be loaded manually
            List<Method> relationshipGetters = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().equals(Set.class))
                .filter(field -> field.getAnnotation(ManyToMany.class) != null)
                .filter(field -> field.getAnnotation(ManyToMany.class).mappedBy().isEmpty())
                .filter(field -> field.getAnnotation(JsonIgnore.class) == null)
                .map(field -> {
                    try {
                        return new PropertyDescriptor(field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed",
                            entityClass.getSimpleName(), field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            int size = 100;
            for (int i = 0; i <= jpaRepository.count() / size; i++) {
                Pageable page = new PageRequest(i, size);
                log.info("Indexing page {} of {}, size {}", i, jpaRepository.count() / size, size);
                Page<T> results = jpaRepository.findAll(page);
                results.map(result -> {
                    // if there are any relationships to load, do it now
                    relationshipGetters.forEach(method -> {
                        try {
                            // eagerly load the relationship set
                            ((Set) method.invoke(result)).size();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
                    return result;
                });
                elasticsearchRepository.saveAll(results.getContent());
            }
        }
        log.info("Elasticsearch: Indexed all rows for {}", entityClass.getSimpleName());
    }
}
