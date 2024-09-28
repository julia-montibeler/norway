package com.app.norway.domain.place;

import java.util.UUID;

public record PlaceDto(UUID Id, String name, String city, String image) {
    public static PlaceDto from(Place place) {
        return new PlaceDto(place.getId(), place.getName(), place.getCity(), place.getImage());
    }
}