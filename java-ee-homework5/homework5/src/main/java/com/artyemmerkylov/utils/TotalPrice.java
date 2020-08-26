package com.artyemmerkylov.utils;

import java.math.BigDecimal;

public class TotalPrice {

    private BigDecimal totalPrice;

    public TotalPrice() {
        totalPrice = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
