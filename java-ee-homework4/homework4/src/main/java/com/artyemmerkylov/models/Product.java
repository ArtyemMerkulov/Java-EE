package com.artyemmerkylov.models;

import java.math.BigDecimal;

public class Product {

    private Integer id;

    private String name;

    private String description;

    private BigDecimal price;

    private String img;

    private String categoryName;

    public Product() {
    }

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
        this.price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.img = "resources/images/products/default.png";
        this.categoryName = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
