package com.artyemmerkylov.models;

import com.artyemmerkylov.service.ProductRepr;

import java.io.Serializable;

public class Position implements Serializable {

    private ProductRepr product;

    private Integer total;

    public Position(ProductRepr product, Integer total) {
        this.product = product;
        this.total = total;
    }

    public ProductRepr getProduct() {
        return product;
    }

    public void setProduct(ProductRepr product) {
        this.product = product;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
