package com.example.api.service;

import com.example.api.dto.LocationDto;
import com.example.api.entity.Location;
import com.example.api.entity.User;
import com.example.api.exception.DBException;
import com.example.api.exception.EntityNotFoundException;
import com.example.api.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class LocationService  {
    private final LocationRepository locationRepository;
    private final UserService userService;

    @Autowired
    public LocationService(LocationRepository locationRepository, UserService userService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
    }

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

    public Location getById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Location"));
    }


    public Location create(LocationDto data, Long userId) {
        User user = userService.getById(userId);

        Location location = new Location();
        location.setLocationName(data.getLocationName());
        location.setLat(data.getLat());
        location.setLng(data.getLng());
        location.setCreatedAt(new Date());
        location.setUser(user);


        try {
            return locationRepository.save(location);
        } catch (Exception e) {
            throw new DBException("Could not save location!");
        }
    }


    public Location update(Long id, LocationDto data, Long userId) {
        User user = userService.getById(userId);

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

    public Location delete(Long id) {
        Optional<Location> location = locationRepository.findById(id);

        if (location.isEmpty()) throw new EntityNotFoundException("Location");

        try {
            locationRepository.deleteById(id);
            return location.get();
        } catch (Exception e) {
            throw new DBException("Could not delete location!");
        }

    }
}
