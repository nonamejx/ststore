package com.ststore.app.repository;

import com.ststore.app.domain.PriceBook;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PriceBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceBookRepository extends JpaRepository<PriceBook, Long> {

}
