package com.artyemmerkylov.utils;

public class Pair<F, S> {

    private final F f;
    private final S s;

    public Pair(F f, S s) {
        this.f = f;
        this.s = s;
    }

    public F getF() {
        return f;
    }

    public S getS() {
        return s;
    }
}
