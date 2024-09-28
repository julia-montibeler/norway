package com.app.norway.respositories;

import com.app.norway.domain.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {
    List<Place> findByCity(String city);
}
