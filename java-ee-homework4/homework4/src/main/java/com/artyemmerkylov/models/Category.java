package com.artyemmerkylov.models;

import java.util.List;

public class Category {

    private Integer id;

    private String name;

    private String description;

    private List<Product> products;

    public Category() {
    }

    public Category(Integer id, String name, String description, List<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public Category(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = null;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }
}
