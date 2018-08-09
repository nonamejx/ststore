package com.ststore.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the PriceBookItem entity.
 */
public class PriceBookItemDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal newPrice;

    private Long productId;

    private String productProductName;

    private Long priceBookId;

    private String priceBookPriceBookName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductProductName() {
        return productProductName;
    }

    public void setProductProductName(String productProductName) {
        this.productProductName = productProductName;
    }

    public Long getPriceBookId() {
        return priceBookId;
    }

    public void setPriceBookId(Long priceBookId) {
        this.priceBookId = priceBookId;
    }

    public String getPriceBookPriceBookName() {
        return priceBookPriceBookName;
    }

    public void setPriceBookPriceBookName(String priceBookPriceBookName) {
        this.priceBookPriceBookName = priceBookPriceBookName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceBookItemDTO priceBookItemDTO = (PriceBookItemDTO) o;
        if (priceBookItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceBookItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceBookItemDTO{" +
            "id=" + getId() +
            ", newPrice=" + getNewPrice() +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", priceBook=" + getPriceBookId() +
            ", priceBook='" + getPriceBookPriceBookName() + "'" +
            "}";
    }
}
