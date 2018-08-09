package com.ststore.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A PriceBookItem.
 */
@Entity
@Table(name = "price_book_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pricebookitem")
public class PriceBookItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "new_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal newPrice;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("")
    private PriceBook priceBook;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public PriceBookItem newPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
        return this;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public Product getProduct() {
        return product;
    }

    public PriceBookItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PriceBook getPriceBook() {
        return priceBook;
    }

    public PriceBookItem priceBook(PriceBook priceBook) {
        this.priceBook = priceBook;
        return this;
    }

    public void setPriceBook(PriceBook priceBook) {
        this.priceBook = priceBook;
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
        PriceBookItem priceBookItem = (PriceBookItem) o;
        if (priceBookItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceBookItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceBookItem{" +
            "id=" + getId() +
            ", newPrice=" + getNewPrice() +
            "}";
    }
}
