package com.artyemmerkylov.service;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface CategoryService {

    void insert(CategoryRepr categoryRepr);

    void update(CategoryRepr categoryRepr);

    void delete(Integer id);

    Optional<CategoryRepr> findById(Integer id);

    List<CategoryRepr> findAll();
}
