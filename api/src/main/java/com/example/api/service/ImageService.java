package com.example.api.service;

import com.example.api.entity.Image;
import com.example.api.entity.Location;
import com.example.api.entity.User;
import com.example.api.exception.DBException;
import com.example.api.exception.EntityNotFoundException;
import com.example.api.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserService userService;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserService userService) {
        this.imageRepository = imageRepository;
        this.userService = userService;
    }

    public List<Image> getAll() {
        try {
            return imageRepository.findAll();
        } catch (Exception e) {
            throw new DBException("Could not fetch images!");
        }    }

    public Image getById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image"));
    }

    public Image create(String data, Location location) {
        User user = userService.getById(location.getUser().getUserId());

        if (user == null) throw new EntityNotFoundException("User");

        Image image = new Image();
        image.setData(data);
        image.setLocation(location);


        try {
            return imageRepository.save(image);
        } catch (Exception e) {
            throw new DBException("Could not save image!");
        }
    }

    public Image update(Long id, String data, Location location) {
        User user = userService.getById(location.getUser().getUserId());

        if (user == null) throw new EntityNotFoundException("User");

        Optional<Image> existingImageOpt = imageRepository.findById(id);

        if (existingImageOpt.isEmpty()) throw new EntityNotFoundException("Location");

        Image existingImage = existingImageOpt.get();

        existingImage.setLocation(location);
        existingImage.setData(data);

        try {
            return imageRepository.save(existingImage);
        } catch (Exception e) {
            throw new DBException("Could not update image!");
        }
    }

    public Image delete(Long id) {
        Optional<Image> image = imageRepository.findById(id);

        if (image.isEmpty()) throw new EntityNotFoundException("Image");

        try {
            imageRepository.delete(image.get());
            return image.get();
        } catch (Exception e) {
            throw new DBException("Could not delete image!");
        }
    }
}
