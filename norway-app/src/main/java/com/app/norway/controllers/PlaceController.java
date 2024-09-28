package com.app.norway.controllers;

import com.app.norway.domain.place.Place;
import com.app.norway.respositories.PlaceRepository;
import com.app.norway.domain.place.PlaceDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("place")
public class PlaceController {

    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);

    @Autowired
    PlaceRepository placeRepository;

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getAllPlaces() {
        try {
            List<Place> places = placeRepository.findAll();
            List<PlaceDto> placesDtos = new ArrayList<>();

            for (Place place : places) {
                PlaceDto placeDto = new PlaceDto(place.getId(), place.getName(), place.getCity(), place.getImage());
                placesDtos.add(placeDto);
            }

            return ResponseEntity.status(HttpStatus.OK).body(placesDtos);
        } catch (Exception e) {
            logger.error("Error fetching all places: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/searchCity/{city}")
    public ResponseEntity<List<PlaceDto>> getPlacesByCity(@PathVariable(value="city") String city) {
        try {
            List<PlaceDto> placesDtos = new ArrayList<>();
            List<Place> places = placeRepository.findByCity(city);

            for (Place place : places) {
                placesDtos.add(PlaceDto.from(place)); // Usa o método estático para converter
            }

            return ResponseEntity.status(HttpStatus.OK).body(placesDtos);
        } catch (Exception e) {
            logger.error("Error fetching places in city '{}': {}", city, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PlaceDto> getPlaceById(@PathVariable(value="id") UUID id) {
        try {
            Optional<Place> place = placeRepository.findById(id);
            if (place.isPresent()) {
                PlaceDto placeDto = new PlaceDto(place.get().getId(), place.get().getName(), place.get().getCity(), place.get().getImage());
                return ResponseEntity.status(HttpStatus.OK).body(placeDto);
            } else {
                logger.warn("Place with ID '{}' not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error fetching place by ID '{}': {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Place> addPlace(@RequestBody @Valid PlaceDto placeDto) {
        try {
            Place place = new Place();
            BeanUtils.copyProperties(placeDto, place);
            return ResponseEntity.status(HttpStatus.CREATED).body(placeRepository.save(place));
        } catch (Exception e) {
            logger.error("Error registering place: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deletePlace(@PathVariable(value="id") String id) {
        try {
            Optional<Place> place = placeRepository.findById(UUID.fromString(id));
            if (place.isEmpty()) {
                logger.warn("Attempt to delete place with ID '{}' that does not exist", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Place not found");
            } else {
                placeRepository.deleteById(UUID.fromString(id));
                return ResponseEntity.status(HttpStatus.OK).body("Place deleted successfully");
            }
        } catch (Exception e) {
            logger.error("Error deleting place with ID '{}': {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting place");
        }
    }
}