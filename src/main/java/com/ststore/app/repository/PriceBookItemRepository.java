package com.ststore.app.repository;

import com.ststore.app.domain.PriceBookItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PriceBookItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceBookItemRepository extends JpaRepository<PriceBookItem, Long> {

}
