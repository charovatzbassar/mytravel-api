package com.example.api.service;

import com.example.api.dto.LocationDto;
import com.example.api.entity.Image;
import com.example.api.entity.Location;
import com.example.api.entity.User;
import com.example.api.exception.DBException;
import com.example.api.exception.EntityNotFoundException;
import com.example.api.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LocationService implements BaseService<Location, LocationDto, Long> {
    private final LocationRepository locationRepository;
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public LocationService(LocationRepository locationRepository, UserService userService, ImageService imageService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public List<Location> getAll() {
        try {
            return locationRepository.findAll();
        } catch (Exception e) {
            throw new DBException("Could not fetch locations!");
        }
    }

    public List<Location> getByUserId(Long userId) {
        try {
            User user = userService.getById(userId);
            return locationRepository.findByUser(user);
        } catch (Exception e) {
            throw new DBException("Could not fetch locations!");
        }
    }

    @Override
    public Location getById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location"));
    }

    @Override
    public Location create(LocationDto data) {
        User user = userService.getById(data.getUserId());

        if (user == null) throw new EntityNotFoundException("User");

        Location location = new Location();
        location.setLocationName(data.getLocationName());
        location.setLat(data.getLat());
        location.setLng(data.getLng());
        location.setCreatedAt(new Date());
        location.setUser(user);

        List<Image> images = new ArrayList<>();

        for (String img : data.getImages()) {
            Image image = imageService.create(img, location);
            images.add(image);
        }

        location.setImages(images);

        try {
            return locationRepository.save(location);
        } catch (Exception e) {
            throw new DBException("Could not save location!");
        }
    }

    @Override
    public Location update(Long id, LocationDto data) {
        User user = userService.getById(data.getUserId());

        if (user == null) throw new EntityNotFoundException("User");

        Optional<Location> existingLocationOpt = locationRepository.findById(id);

        if (existingLocationOpt.isEmpty()) throw new EntityNotFoundException("Location");

        Location existingLocation = existingLocationOpt.get();

        existingLocation.setLocationName(data.getLocationName());
        existingLocation.setLat(data.getLat());
        existingLocation.setLng(data.getLng());
        existingLocation.setUser(user);

        try {
            return locationRepository.save(existingLocation);
        } catch (Exception e) {
            throw new DBException("Could not update location!");
        }
    }

    @Override
    public Location delete(Long id) {
        // only the user who created can delete
        Optional<Location> location = locationRepository.findById(id);

        if (location.isEmpty()) throw new EntityNotFoundException("Location");

        try {
            locationRepository.delete(location.get());
            return location.get();
        } catch (Exception e) {
            throw new DBException("Could not delete location!");
        }

    }
}
