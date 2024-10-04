package com.example.api.service;

import com.example.api.dto.UserDto;
import com.example.api.entity.User;
import com.example.api.exception.DBException;
import com.example.api.exception.EntityNotFoundException;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.swing.text.html.parser.Entity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements BaseService<User, UserDto, Long> {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new DBException("Could not fetch users!");
        }
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User create(UserDto data) {

        User user = new User();
        user.setEmail(data.getEmail());
        user.setUsername(data.getUsername());
        user.setPasswordHash(data.getPassword());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setVerified(false);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new DBException("Could not save user!");
        }
    }

    @Override
    public User update(Long id, UserDto data) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            throw new RuntimeException("User does not exist!");
        }

        User existingUser = existingUserOpt.get();

        existingUser.setUsername(data.getUsername());
        existingUser.setPasswordHash(data.getPassword());
        existingUser.setEmail(data.getEmail());
        existingUser.setUpdatedAt(new Date());

        try {
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new DBException("Could not update user!");
        }    }

    @Override
    public User delete(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) throw new RuntimeException("User does not exist!");

        try {
            userRepository.delete(user.get());

            return user.get();
        } catch (Exception e) {
            throw new DBException("Could not delete user!");
        }
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void verifyUser(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
            user.setVerified(true);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Could not verify user!");
        }
    }


}
