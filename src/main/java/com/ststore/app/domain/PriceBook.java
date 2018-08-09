package com.ststore.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PriceBook.
 */
@Entity
@Table(name = "price_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pricebook")
public class PriceBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "price_book_name", nullable = false)
    private String priceBookName;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriceBookName() {
        return priceBookName;
    }

    public PriceBook priceBookName(String priceBookName) {
        this.priceBookName = priceBookName;
        return this;
    }

    public void setPriceBookName(String priceBookName) {
        this.priceBookName = priceBookName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public PriceBook createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PriceBook priceBook = (PriceBook) o;
        if (priceBook.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceBook.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceBook{" +
            "id=" + getId() +
            ", priceBookName='" + getPriceBookName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
