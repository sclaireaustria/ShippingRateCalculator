package org.example.model;

import java.util.List;

public record OrderRequest(List<Parcel> parcels, boolean isSpeedyShipping) {
}
