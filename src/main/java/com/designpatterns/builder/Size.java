package com.designpatterns.builder;

/** How big a hungry person's pizza is. */
public enum Size {
    SMALL(8),
    MEDIUM(12),
    LARGE(16);

    private final int inches;

    Size(int inches) {
        this.inches = inches;
    }

    public int inches() {
        return inches;
    }
}
