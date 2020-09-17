package com.artyemmerkylov.service;

import com.artyemmerkylov.models.Position;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
public class CartServiceImpl implements CartService, Serializable {

    private HashMap<Integer, Position> positionsMap = new HashMap<>();

    private BigDecimal totalPrice = BigDecimal.valueOf(0);

    @Override
    public void add(ProductRepr product) {
        Position position = positionsMap.getOrDefault(product.getId(),
                new Position(product, 0));
        position.setTotal(position.getTotal() + 1);

        positionsMap.put(product.getId(), position);
        totalPrice = totalPrice.add(position.getProduct().getPrice());
    }

    @Override
    public void delete(Integer id) {
        Position position = positionsMap.get(id);

        totalPrice = totalPrice.subtract(new BigDecimal(position.getTotal()).multiply(position.getProduct().getPrice()));
        positionsMap.remove(id);
    }

    @Override
    public List<Position> getAllPositions() {
        return positionsMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
