package com.example.api.repository;

import com.example.api.entity.Image;
import com.example.api.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByLocation(Location location);
}
