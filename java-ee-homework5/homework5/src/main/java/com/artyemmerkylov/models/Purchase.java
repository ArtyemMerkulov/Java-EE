package com.artyemmerkylov.models;

import javax.faces.event.ValueChangeEvent;

public final class Purchase<F extends Product, S extends Integer> {

    private F product;

    private S amount;

    public Purchase() {
    }

    public Purchase(F product, S amount) {
        this.product = product;
        this.amount = amount;
    }

    public F getProduct() {
        return product;
    }

    public void setProduct(final F product) {
        this.product = product;
    }

    public S getAmount() {
        return amount;
    }

    public void setAmount(final S amount) {
        this.amount = amount;
    }

    public void changeAmount(ValueChangeEvent event) {
        this.amount = (S) event.getNewValue();
    }
}
