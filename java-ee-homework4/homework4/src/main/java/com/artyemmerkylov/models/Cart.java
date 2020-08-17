package com.artyemmerkylov.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cart {

    private Map<Integer, Integer> productsParams;

    public Cart() {
        this.productsParams = new HashMap<>();
    }

    public Cart(HashMap<Integer, Integer> productsParams) {
        this.productsParams = productsParams;
    }

    public Map<Integer, Integer> getProductsParams() {
        return productsParams;
    }

    public List<Integer> getProductsIds() {
        return productsParams.keySet().stream().collect(Collectors.toList());
    }

    public List<Integer> getAmountOfProducts() {
        return productsParams.values().stream().collect(Collectors.toList());
    }

    public Integer getTotalProducts() {
        return productsParams.values().stream().reduce((o1, o2)->o1 + o2).get();
    }

    public void addProductParamsById(Integer id) {
        productsParams.put(id, productsParams.getOrDefault(id, 0) + 1);
    }

    public void clear() {
        this.productsParams.clear();
    }
}
