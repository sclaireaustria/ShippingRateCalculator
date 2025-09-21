package org.example.model;

/**
 * Contains rates depending on parcel size
 * */
public enum ParcelCategories {

    SMALL(10, 3),
    MEDIUM(50, 8),
    LARGE(100, 15),
    XL(Double.MAX_VALUE, 25);

    private final double dimensionLimit;
    private final double cost;

    ParcelCategories(double dimensionLimit, double cost) {
        this.dimensionLimit = dimensionLimit;
        this.cost = cost;
    }

    public double getDimensionLimit() {
        return dimensionLimit;
    }

    public double getCost() {
        return cost;
    }
}

