package ru.bikmag.carsharing.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.bikmag.carsharing.models.Rental;
import ru.bikmag.carsharing.services.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/{rentId}")
    public Rental getRental(@PathVariable Long rentId) {
        return rentalService.getRentalById(rentId);
    }

    @GetMapping("/current")
    public Rental getCurrentRental(Authentication authentication) {
        String username = authentication.getName();
        return rentalService.getCurrentRentalByUsername(username);
    }

    @PostMapping("/rent/{carId}")
    public Rental rentCar(@PathVariable Long carId, Authentication authentication) {
        String username = authentication.getName(); // Получаем имя текущего пользователя
        return rentalService.rentCar(carId, username);
    }

    @PostMapping("/finish/{rentalId}")
    public Rental finishRental(@PathVariable Long rentalId) {
        return rentalService.finishRental(rentalId);
    }
}