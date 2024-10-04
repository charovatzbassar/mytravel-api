package com.example.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @NotEmpty(message = "Location name cannot be empty!")
    @Column(name = "location_name")
    private String locationName;

    @Column(name = "created_at")
    private Date createdAt;

    @NotNull(message = "Latitude cannot be empty!")
    @Column(name = "lat")
    private Double lat;

    @NotNull(message = "Longitude cannot be empty!")
    @Column(name = "long")
    private Double lng;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "location", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Image> images;

    public Location() {}

    public Location(Long locationId, String locationName, Date createdAt, Double lat, Double lng, User user, List<Image> images) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.createdAt = createdAt;
        this.lat = lat;
        this.lng = lng;
        this.user = user;
        this.images = images;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", createdAt=" + createdAt +
                ", lat=" + lat +
                ", lng=" + lng +
                ", user=" + user +
                ", images=" + images +
                '}';
    }
}
