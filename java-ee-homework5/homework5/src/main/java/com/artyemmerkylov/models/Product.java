package com.artyemmerkylov.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(scale = 4, precision = 8, nullable = false)
    private BigDecimal price;

    @Column(length = 54, nullable = false)
    private String img;

    @Column(name = "category_id", insertable = false, updatable = false, nullable = false)
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public Product() {
    }

    public Product(Integer id, String name, String description, BigDecimal price, String img, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = img;
        this.categoryId = categoryId;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.category.setId(this.categoryId);
        this.category.setName(categoryName);
    }

    public void info() {
        System.out.println("Product: " + id + " " + name + " " + description + " " + price + " " + img + " " + categoryId);
    }
}
