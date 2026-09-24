package com.nibm.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "supplements")
public class Supplement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Supplement name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SupplementCategory category;

    @Column(length = 100)
    private String brand;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Column(name = "price_lkr", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceLkr;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be negative")
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "serving_size", length = 50)
    private String servingSize;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Supplement() {
    }

    public Supplement(String name, SupplementCategory category, String brand, BigDecimal priceLkr, Integer stockQuantity, String servingSize, String description) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.priceLkr = priceLkr;
        this.stockQuantity = stockQuantity;
        this.servingSize = servingSize;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplementCategory getCategory() {
        return category;
    }

    public void setCategory(SupplementCategory category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPriceLkr() {
        return priceLkr;
    }

    public void setPriceLkr(BigDecimal priceLkr) {
        this.priceLkr = priceLkr;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
