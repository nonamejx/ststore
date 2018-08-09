package com.ststore.app.repository;

import com.ststore.app.domain.ImageAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImageAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageAttributeRepository extends JpaRepository<ImageAttribute, Long> {

}
