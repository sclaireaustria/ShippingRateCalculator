package org.example.model;

/**
 * Contains rates depending on parcel size
 * */
public enum ParcelCategories {

    SMALL(10, 1, 3),
    MEDIUM(50, 3, 8),
    LARGE(100, 6, 15),
    XL(Double.MAX_VALUE, 10, 25);

    private final double dimensionLimit;
    private final double weightLimit;
    private final double cost;

    ParcelCategories(double dimensionLimit, double weightLimit, double cost) {
        this.dimensionLimit = dimensionLimit;
        this.weightLimit = weightLimit;
        this.cost = cost;
    }

    public double getDimensionLimit() {
        return dimensionLimit;
    }

    public double getCost() {
        return cost;
    }

    public double getWeightLimit() {
        return weightLimit;
    }
}

