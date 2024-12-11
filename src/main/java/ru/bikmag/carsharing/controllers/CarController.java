package ru.bikmag.carsharing.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.bikmag.carsharing.models.Car;
import org.springframework.web.bind.annotation.*;
import ru.bikmag.carsharing.services.CarService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

//    @GetMapping
//    public List<Car> getAllCars() {
//        return carService.getAllCars();
//    }

    @GetMapping
    public List<Car> getAvailableCars(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String sortBy) {
        return carService.getFilteredAndSortedCars(status, brand, model, sortBy);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        return carService.getCarById(carId);
    }

    @PostMapping
    public Car addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @PatchMapping("/{carId}/status")
//    @PreAuthorize("hasRole('MECHANIC')")
    public Car updateCarStatus(@PathVariable Long carId, @RequestParam Car.CarStatus status) {
        return carService.updateCarStatus(carId, status);
    }

    @PatchMapping("/{carId}/repair")
    @PreAuthorize("hasRole('MECHANIC')")
    public ResponseEntity<String> updateRepairStatus(
            @PathVariable Long carId,
            @RequestParam boolean inRepair) {
        carService.updateRepairStatus(carId, inRepair);
        return ResponseEntity.ok("Car repair status updated successfully");
    }
}
