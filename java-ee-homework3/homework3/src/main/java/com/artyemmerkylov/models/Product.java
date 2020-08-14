package com.artyemmerkylov.models;

import java.math.BigDecimal;

public class Product {

    private Integer id;

    private String name;

    private String description;

    private BigDecimal price;

    private String img;

    private String categoryName;

    public Product(Integer id, String name, String description, BigDecimal price, String img, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = img;
        this.categoryName = categoryName;
    }

    public Product(Integer id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = "resources/images/products/default.png";
        this.categoryName = null;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
