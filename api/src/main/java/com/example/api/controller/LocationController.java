package com.example.api.controller;

import com.example.api.dto.LocationDto;
import com.example.api.entity.Location;
import com.example.api.exception.EntityNotFoundException;
import com.example.api.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("")
    public ResponseEntity<List<Location>> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Location>> getByUserId(HttpServletRequest request) {
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        return ResponseEntity.ok(locationService.getByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<Location> create(@Valid @RequestBody LocationDto data) {
        return ResponseEntity.ok(locationService.create(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@Valid @RequestBody LocationDto data, @PathVariable Long id) {
        return ResponseEntity.ok(locationService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Location> delete(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.delete(id));
    }
}
