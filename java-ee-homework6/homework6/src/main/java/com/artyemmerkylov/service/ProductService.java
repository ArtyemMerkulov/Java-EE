package com.artyemmerkylov.service;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface ProductService {

    void insert(ProductRepr productRepr);

    void update(ProductRepr productRepr);

    void delete(Integer id);

    Optional<ProductRepr> findById(Integer id);

    List<ProductRepr> findAll();
}

