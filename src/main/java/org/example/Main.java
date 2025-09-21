package org.example;

import org.example.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * Assumptions:
     * - Weight is always in kg
     * */

    public static final double OVERWEIGHT_FEE_PER_KG = 2;
    public static final double HEAVY_OVERWEIGHT_FEE_PER_KG = 1;

    public static OrderResult calculateRate(OrderRequest orderRequest) {

        List<Parcel> parcels = orderRequest.parcels();
        List<ParcelResult> parcelResult = new ArrayList<>();

        double initialCost = calculateInitialShippingCost(parcels, parcelResult);
        double speedyShippingCost = calculateSpeedyShipping(orderRequest.isSpeedyShipping(), initialCost);
        double totalCost = calculateTotalShippingCost(initialCost, speedyShippingCost);

        return new OrderResult(parcelResult, speedyShippingCost, totalCost);
    }

    public static double calculateInitialShippingCost(List<Parcel> parcels, List<ParcelResult> parcelResult) {
        double initialCost = 0;

        for(Parcel parcel : parcels) {

            // Get the parcel category
            ParcelCategories category = getParcelCategory(parcel);

            double parcelCost = checkBetterRate(parcel, category, parcelResult);

            initialCost += parcelCost;
        }

        return initialCost;
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

    public static double checkBetterRate(Parcel parcel, ParcelCategories category, List<ParcelResult> parcelResult) {
        double overweightFee = calculateOverweightFee(parcel, category);
        double heavyParcelCost = calculateHeavyParcelCost(parcel);

        double parcelCost = category.getCost();

        if (overweightFee > heavyParcelCost) {
            parcelCost = heavyParcelCost;

            parcelResult.add(new ParcelResult(parcel, parcelCost, ParcelCategories.HEAVY.name()));
        } else {
            parcelCost = category.getCost() + overweightFee;

            parcelResult.add(new ParcelResult(parcel, parcelCost, category.name()));
        }

        return parcelCost;
    }

    public static double calculateOverweightFee(Parcel parcel, ParcelCategories category) {
        double overweightFee = 0;

        if (parcel.weight() > category.getWeightLimit()) {
            double excessWeight = parcel.weight() - category.getWeightLimit();
            overweightFee = excessWeight * OVERWEIGHT_FEE_PER_KG;
        }

        return overweightFee;
    }

    public static double calculateHeavyParcelCost(Parcel parcel) {
        double heavyParcelCost = ParcelCategories.HEAVY.getCost();

        if (parcel.weight() > ParcelCategories.HEAVY.getWeightLimit()) {
            heavyParcelCost = (parcel.weight() - ParcelCategories.HEAVY.getWeightLimit())
                    * HEAVY_OVERWEIGHT_FEE_PER_KG;
        }

        return heavyParcelCost;
    }

    public static double calculateSpeedyShipping(boolean isSpeedyShipping, double initialCost) {
        return isSpeedyShipping ? initialCost : 0;
    }

    public static double calculateTotalShippingCost(double initialCost, double speedyShippingCost) {
        return initialCost + speedyShippingCost;
    }



}