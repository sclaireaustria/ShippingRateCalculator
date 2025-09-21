package org.example;

import org.example.model.OrderResult;
import org.example.model.Parcel;
import org.example.model.ParcelCategories;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final double ALL_SIZES_COST = ParcelCategories.SMALL.getCost()
            + ParcelCategories.MEDIUM.getCost() + ParcelCategories.LARGE.getCost()
            + ParcelCategories.XL.getCost();

    @Test
    void testSmallParcelCategory() {
        Parcel parcel = new Parcel(5, 5, 5);
        ParcelCategories category = Main.getParcelCategory(parcel);

        assertEquals(ParcelCategories.SMALL, category);
    }

    @Test
    void testMediumParcelCategory() {
        Parcel parcel = new Parcel(20, 30, 40);
        ParcelCategories category = Main.getParcelCategory(parcel);

        assertEquals(ParcelCategories.MEDIUM, category);
    }

    @Test
    void testLargeParcelCategory() {
        Parcel parcel = new Parcel(70, 80, 90);
        ParcelCategories category = Main.getParcelCategory(parcel);

        assertEquals(ParcelCategories.LARGE, category);
    }

    @Test
    void testXLParcelCategory() {
        Parcel parcel = new Parcel(120, 50, 80);
        ParcelCategories category = Main.getParcelCategory(parcel);

        assertEquals(ParcelCategories.XL, category);
    }

    @Test
    void testBoundaryCases() {
        Parcel smallBoundary = new Parcel(9.9, 9.9, 9.9); // Small
        Parcel mediumMinBoundary = new Parcel(10, 10, 10); // Medium
        Parcel mediumMaxBoundary = new Parcel(49.9, 20, 10); // Medium
        Parcel largeMinBoundary = new Parcel(50, 20, 10);  // Large
        Parcel largeMaxBoundary = new Parcel(99.9, 20, 10);  // Large
        Parcel xlBoundary = new Parcel(100, 20, 10);      // XL

        assertEquals(ParcelCategories.SMALL, Main.getParcelCategory(smallBoundary));
        assertEquals(ParcelCategories.MEDIUM, Main.getParcelCategory(mediumMinBoundary));
        assertEquals(ParcelCategories.MEDIUM, Main.getParcelCategory(mediumMaxBoundary));
        assertEquals(ParcelCategories.LARGE, Main.getParcelCategory(largeMinBoundary));
        assertEquals(ParcelCategories.LARGE, Main.getParcelCategory(largeMaxBoundary));
        assertEquals(ParcelCategories.XL, Main.getParcelCategory(xlBoundary));
    }

    @Test
    void testOrderTotalCost() {
        List<Parcel> parcels = Arrays.asList(
                new Parcel(5, 5, 5),
                new Parcel(20, 20, 20),
                new Parcel(80, 40, 30),
                new Parcel(100, 40, 30)
        );

        OrderResult result = Main.calculateRate(parcels);

        assertEquals(ALL_SIZES_COST, result.totalShippingCost(), 0.001);
        assertEquals(4, result.parcels().size());
    }
}