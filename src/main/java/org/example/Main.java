package org.example;

import org.example.model.OrderResult;
import org.example.model.Parcel;
import org.example.model.ParcelCategories;
import org.example.model.ParcelResult;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
    }

    public static OrderResult calculateRate(List<Parcel> parcels) {

        List<ParcelResult> parcelResult = new ArrayList<>();
        double totalCost = 0;

        for(Parcel parcel : parcels) {
            // Get the parcel category
            ParcelCategories category = getParcelCategory(parcel);
            parcelResult.add(new ParcelResult(parcel, category.getCost(), category.name()));

            totalCost += category.getCost();
        }

        return new OrderResult(parcelResult, totalCost);
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
}