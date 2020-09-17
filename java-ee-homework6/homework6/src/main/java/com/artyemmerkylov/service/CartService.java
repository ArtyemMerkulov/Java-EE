package com.artyemmerkylov.service;

import com.artyemmerkylov.models.Position;

import javax.ejb.Remote;
import java.math.BigDecimal;
import java.util.List;

@Remote
public interface CartService {

    void add(ProductRepr productRepr);

    void delete(Integer id);

    List<Position> getAllPositions();

    BigDecimal getTotalPrice();

}
