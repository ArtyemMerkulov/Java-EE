package com.artyemmerkylov.models;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private Integer id;

    private String buyerName;

    private String phone;

    private String details;

    private List<Purchase> purchaseList;

    private BigDecimal totalPrice;

    public Order() {
    }

    public Order(Integer id, String buyerName, String phone, String details) {
        this.id = id;
        this.buyerName = buyerName;
        this.phone = phone;
        this.details = details;
        this.purchaseList = null;
        this.totalPrice = null;
    }

    public Order(Integer id, String buyerName, String phone, String details, List<Purchase> purchaseList) {
        this.id = id;
        this.buyerName = buyerName;
        this.phone = phone;
        this.details = details;
        this.purchaseList = purchaseList;
        this.totalPrice = new BigDecimal(0);

        initTotalPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
        initTotalPrice();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    private void initTotalPrice() {
        for (Purchase purchase : purchaseList)
            this.totalPrice = this.totalPrice.add(purchase.getProduct().getPrice().multiply(BigDecimal.valueOf(purchase.getAmount())));
        this.totalPrice = this.totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
