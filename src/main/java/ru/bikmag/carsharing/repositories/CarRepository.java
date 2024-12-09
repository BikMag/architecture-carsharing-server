package ru.bikmag.carsharing.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.bikmag.carsharing.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(Car.CarStatus status);
    List<Car> findAll(Specification<Car> specification, Sort sort);
}
