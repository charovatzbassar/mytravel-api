package com.example.api.repository;

import ch.qos.logback.core.util.Loader;
import com.example.api.entity.Location;
import com.example.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByUser(User user);
}
