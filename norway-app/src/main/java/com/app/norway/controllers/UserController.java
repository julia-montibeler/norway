package com.app.norway.controllers;

import com.app.norway.domain.user.User;
import com.app.norway.domain.user.UserDTO;
import com.app.norway.domain.user.UserFavoritesDTO;
import com.app.norway.respositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") UUID id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                logger.warn("User with ID '{}' not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            User user = userOpt.get();
            UserDTO userDto = new UserDTO(user.getId(), user.getLogin(), user.getRole());

            return ResponseEntity.status(HttpStatus.OK).body(userDto);

        } catch (Exception e) {
            logger.error("Error fetching user by ID '{}': {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/favorites")
    public ResponseEntity<UserFavoritesDTO> getFavoritesById(@PathVariable(value = "id") UUID id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                logger.warn("User with ID '{}' not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            User user = userOpt.get();
            UserFavoritesDTO favorites = new UserFavoritesDTO(new ArrayList<>(user.getFavorites()));

            return ResponseEntity.status(HttpStatus.OK).body(favorites);

        } catch (Exception e) {
            logger.error("Error fetching favorites for user ID '{}': {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDtos = new ArrayList<>();

            for (User user : users) {
                userDtos.add(new UserDTO(user.getId(), user.getLogin(), user.getRole()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(userDtos);

        } catch (Exception e) {
            logger.error("Error fetching all users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/favorites")
    public ResponseEntity<String> addFavorites(@PathVariable(value = "id") UUID id, @RequestBody UserFavoritesDTO favoritesDto) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                logger.warn("User with ID '{}' not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            User user = userOpt.get();
            user.getFavorites().addAll(favoritesDto.favorites());

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Favorites added successfully");

        } catch (Exception e) {
            logger.error("Error adding favorites for user ID '{}': {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding favorites");
        }
    }
}