package com.artyemmerkylov.service;

import com.artyemmerkylov.models.Category;

public class CategoryRepr {

    private Integer id;

    private String name;

    private String description;

    public CategoryRepr() {
    }

    public CategoryRepr(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CategoryRepr(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
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
}
