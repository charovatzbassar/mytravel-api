package com.example.api.entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "data")
    private String data;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    public Image() {}

    public Image(Long imageId, String data, Location location) {
        this.imageId = imageId;
        this.data = data;
        this.location = location;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", data=" + data +
                ", location=" + location +
                '}';
    }
}
