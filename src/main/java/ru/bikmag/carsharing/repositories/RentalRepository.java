package ru.bikmag.carsharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikmag.carsharing.models.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUsername(String username);
}
