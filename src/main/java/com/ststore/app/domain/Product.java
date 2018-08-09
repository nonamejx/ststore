package com.ststore.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "capital_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal capitalPrice;

    @NotNull
    @Column(name = "sale_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal salePrice;

    @Column(name = "product_description")
    private String productDescription;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ImageAttribute> imageAttributes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Unit unit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public Product productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getCapitalPrice() {
        return capitalPrice;
    }

    public Product capitalPrice(BigDecimal capitalPrice) {
        this.capitalPrice = capitalPrice;
        return this;
    }

    public void setCapitalPrice(BigDecimal capitalPrice) {
        this.capitalPrice = capitalPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public Product salePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Product productDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Set<ImageAttribute> getImageAttributes() {
        return imageAttributes;
    }

    public Product imageAttributes(Set<ImageAttribute> imageAttributes) {
        this.imageAttributes = imageAttributes;
        return this;
    }

    public Product addImageAttribute(ImageAttribute imageAttribute) {
        this.imageAttributes.add(imageAttribute);
        imageAttribute.setProduct(this);
        return this;
    }

    public Product removeImageAttribute(ImageAttribute imageAttribute) {
        this.imageAttributes.remove(imageAttribute);
        imageAttribute.setProduct(null);
        return this;
    }

    public void setImageAttributes(Set<ImageAttribute> imageAttributes) {
        this.imageAttributes = imageAttributes;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Unit getUnit() {
        return unit;
    }

    public Product unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", capitalPrice=" + getCapitalPrice() +
            ", salePrice=" + getSalePrice() +
            ", productDescription='" + getProductDescription() + "'" +
            "}";
    }
}
