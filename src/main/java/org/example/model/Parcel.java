package org.example.model;

public record Parcel(double height, double width, double length, double weight) {

    public double getMaxDimension() {
        return Math.max(length, Math.max(height, width));
    }
}
