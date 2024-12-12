package ru.bikmag.carsharing.services;

import org.springframework.stereotype.Service;
import ru.bikmag.carsharing.models.Car;
import ru.bikmag.carsharing.models.Rental;
import ru.bikmag.carsharing.repositories.CarRepository;
import ru.bikmag.carsharing.repositories.RentalRepository;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RentalService {

    private final CarRepository carRepository;
    private final RentalRepository rentalRepository;

    public RentalService(CarRepository carRepository, RentalRepository rentalRepository) {
        this.carRepository = carRepository;
        this.rentalRepository = rentalRepository;
    }

    public Rental getRentalById(Long id) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);

        if (rentalOptional.isEmpty()) {
            throw new RuntimeException("Rental not found");
        }

        return rentalOptional.get();
    }

    public Rental getCurrentRentalByUsername(String username) {
        Optional<Rental> rentalOptional = rentalRepository
                .findByUsername(username)
                .stream()
                .filter(rental -> rental.getEndTime() == null)
                .findFirst();

        if (rentalOptional.isEmpty()) {
            return null;
        }

        Rental currentRental = rentalOptional.get();
        if (currentRental.getEndTime() != null) {
            return null;
        }

        return currentRental;
    }

    public Rental rentCar(Long carId, String username) {
        Optional<Car> carOptional = carRepository.findById(carId);

        if (carOptional.isEmpty()) {
            throw new RuntimeException("Car not found");
        }

        Car car = carOptional.get();

        if (car.getStatus() != Car.CarStatus.AVAILABLE) {
            throw new RuntimeException("Car is not available for rent");
        }

        car.setStatus(Car.CarStatus.RENTED);
        carRepository.save(car);

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUsername(username);
        rental.setStartTime(LocalDateTime.now());

        return rentalRepository.save(rental);
    }

    public Rental finishRental(Long rentalId) {
        Optional<Rental> rentalOptional = rentalRepository.findById(rentalId);

        if (rentalOptional.isEmpty()) {
            throw new RuntimeException("Rental not found");
        }

        Rental rental = rentalOptional.get();
        if (rental.getEndTime() != null) {
            throw new RuntimeException("Rental is already finished");
        }

        DecimalFormat df = new DecimalFormat("#.##");
        rental.setEndTime(LocalDateTime.now());
        rentalRepository.save(rental);
        rental.setPrice(
                Double.parseDouble(df.format(
                    rental.getCar().getPricePerMinute() * (
                                Duration.between(
                                        rental.getStartTime(),
                                        rental.getEndTime()
                                ).toMinutes() + 1L
                    )
                ))
        );
        rentalRepository.save(rental);

        Car car = rental.getCar();
        car.setStatus(Car.CarStatus.AVAILABLE);
        carRepository.save(car);

        return rental;
    }
}
