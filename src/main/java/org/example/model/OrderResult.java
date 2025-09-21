package org.example.model;

import java.util.List;

public record OrderResult(List<ParcelResult> parcels, double speedyShippingCost, double totalShippingCost) {

}
