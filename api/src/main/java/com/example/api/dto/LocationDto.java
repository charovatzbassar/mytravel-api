package com.example.api.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class LocationDto {
    @NotNull
    @NotBlank(message = "Location name cannot be blank!")
    private String locationName;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    private Long userId;

    @NotNull
    private List<String> images;

    public LocationDto() {}

    public LocationDto(String locationName, Double lat, Double lng, Long userId, List<String> images) {
        this.locationName = locationName;
        this.lat = lat;
        this.lng = lng;
        this.userId = userId;
        this.images = images;
    }

    public @NotNull String getLocationName() {
        return locationName;
    }

    public void setLocationName(@NotNull String locationName) {
        this.locationName = locationName;
    }


    public @NotNull Double getLat() {
        return lat;
    }

    public void setLat(@NotNull Double lat) {
        this.lat = lat;
    }

    public @NotNull Double getLng() {
        return lng;
    }

    public void setLng(@NotNull Double lng) {
        this.lng = lng;
    }

    public @NotNull Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull Long userId) {
        this.userId = userId;
    }

    public @NotNull List<String> getImages() {
        return images;
    }

    public void setImages(@NotNull List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "locationName='" + locationName + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", userId=" + userId +
                ", images=" + images +
                '}';
    }
}

