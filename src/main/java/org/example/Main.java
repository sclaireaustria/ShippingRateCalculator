package org.example;

import org.example.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static OrderResult calculateRate(OrderRequest orderRequest) {

        List<Parcel> parcels = orderRequest.parcels();
        List<ParcelResult> parcelResult = new ArrayList<>();
        double initialCost = 0;

        for(Parcel parcel : parcels) {
            // Get the parcel category
            ParcelCategories category = getParcelCategory(parcel);
            parcelResult.add(new ParcelResult(parcel, category.getCost(), category.name()));

            initialCost += category.getCost();
        }

        double speedyShippingCost = calculateSpeedyShipping(orderRequest.isSpeedyShipping(), initialCost);
        double totalCost = calculateTotalShippingCost(initialCost, speedyShippingCost);

        return new OrderResult(parcelResult, speedyShippingCost, totalCost);
    }

    public static ParcelCategories getParcelCategory(Parcel parcel) {
        double parcelMaxDimension = parcel.getMaxDimension();

        for (ParcelCategories category : ParcelCategories.values()) {
            if (parcelMaxDimension < category.getDimensionLimit()) {
                return category;
            }
        }

        return ParcelCategories.XL;
    }

    public static double calculateSpeedyShipping(boolean isSpeedyShipping, double initialCost) {
        return isSpeedyShipping ? initialCost : 0;
    }

    public static double calculateTotalShippingCost(double initialCost, double speedyShippingCost) {
        return initialCost + speedyShippingCost;
    }

}