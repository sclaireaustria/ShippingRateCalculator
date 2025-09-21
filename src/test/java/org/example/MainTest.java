package org.example;

import org.example.model.OrderRequest;
import org.example.model.OrderResult;
import org.example.model.Parcel;
import org.example.model.ParcelCategories;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.example.Main.OVERWEIGHT_FEE_PER_KG;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final double ALL_SIZES_COST = ParcelCategories.SMALL.getCost()
            + ParcelCategories.MEDIUM.getCost() + ParcelCategories.LARGE.getCost()
            + ParcelCategories.XL.getCost();

    @Test
    void testSmallParcelCategory() {
        Parcel parcel = new Parcel(5, 5, 5, 1);
        ParcelCategories category = Main.getParcelCategory(parcel);

        assertEquals(ParcelCategories.SMALL, category);
    }

    @Test
    void testMediumParcelCategory() {
        Parcel parcel = new Parcel(20, 30, 40, 3);
        ParcelCategories category = Main.getParcelCategory(parcel);

        assertEquals(ParcelCategories.MEDIUM, category);
    }

    @Test
    void testLargeParcelCategory() {
        Parcel parcel = new Parcel(70, 80, 90, 6);
        ParcelCategories category = Main.getParcelCategory(parcel);

        assertEquals(ParcelCategories.LARGE, category);
    }

    @Test
    void testXLParcelCategory() {
        Parcel parcel = new Parcel(120, 50, 80, 10);
        ParcelCategories category = Main.getParcelCategory(parcel);

        assertEquals(ParcelCategories.XL, category);
    }

    @Test
    void testBoundaryCases() {
        Parcel smallBoundary = new Parcel(9.9, 9.9, 9.9, 1); // Small
        Parcel mediumMinBoundary = new Parcel(10, 10, 10, 3); // Medium
        Parcel mediumMaxBoundary = new Parcel(49.9, 20, 10, 3); // Medium
        Parcel largeMinBoundary = new Parcel(50, 20, 10, 6);  // Large
        Parcel largeMaxBoundary = new Parcel(99.9, 20, 10, 6);  // Large
        Parcel xlBoundary = new Parcel(100, 20, 10, 10);      // XL

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
                new Parcel(5, 5, 5, 1),
                new Parcel(20, 20, 20, 3),
                new Parcel(80, 40, 30, 6),
                new Parcel(100, 40, 30, 10)
        );

        OrderResult result = Main.calculateRate(new OrderRequest(parcels,false));

        assertEquals(ALL_SIZES_COST, result.totalShippingCost(), 0.001);
        assertEquals(4, result.parcels().size());
    }

    /**
     * Parcel 1: small, overweight
     * Parcel 2: medium
     * Parcel 3: large
     * Parcel 4: XL
     * */
    @Test
    void testOrderTotalCostOverweight() {

        List<Parcel> parcels = Arrays.asList(
                new Parcel(5, 5, 5, 2),
                new Parcel(20, 20, 20, 3),
                new Parcel(80, 40, 30, 6),
                new Parcel(100, 40, 30, 10)
        );

        OrderResult result = Main.calculateRate(new OrderRequest(parcels,false));

        assertEquals(ALL_SIZES_COST+2, result.totalShippingCost(), 0.001);
        assertEquals(4, result.parcels().size());
    }

    @Test
    void testCalculateSpeedyShippingCost_true() {
        double speedyShippingCost = Main.calculateSpeedyShipping(true, ALL_SIZES_COST);
        assertEquals(ALL_SIZES_COST, speedyShippingCost, 0.001);
    }

    @Test
    void testCalculateSpeedyShippingCost_false() {
        double speedyShippingCost = Main.calculateSpeedyShipping(false, ALL_SIZES_COST);
        assertEquals(0, speedyShippingCost, 0.001);
    }

    @Test
    void testCalculateTotalShippingCost() {

        double totalCost = Main.calculateTotalShippingCost(ALL_SIZES_COST, ALL_SIZES_COST);
        assertEquals(ALL_SIZES_COST*2, totalCost, 0.001);
    }

    @Test
    void testCalculateOverweightFee() {
        Parcel smallOverweight = new Parcel(5, 5, 5, 2);

        double overweightFee = Main.calculateOverweightFee(smallOverweight, ParcelCategories.SMALL);

        assertEquals(1*OVERWEIGHT_FEE_PER_KG,  overweightFee, 0.001);
    }
}