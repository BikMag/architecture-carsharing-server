package ru.bikmag.carsharing.services;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.bikmag.carsharing.models.Car;
import ru.bikmag.carsharing.repositories.CarRepository;
import ru.bikmag.carsharing.specifications.CarSpecifications;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public ResponseEntity<Car> getCarById(long id) {
        return carRepository.findById(id)
                .map(ResponseEntity::ok) // Если автомобиль найден, возвращаем его с HTTP статусом 200
                .orElse(ResponseEntity.notFound().build()); // Если нет, возвращаем 404
    }

    public Car addCar(Car car) {
        // Устанавливаем статус по умолчанию, если он не указан
        if (car.getStatus() == null) {
            car.setStatus(Car.CarStatus.AVAILABLE);
        }
        return carRepository.save(car);
    }


    public Car updateCarStatus(Long carId, Car.CarStatus status) {
        Optional<Car> carOptional = carRepository.findById(carId);

        if (carOptional.isEmpty()) {
            throw new RuntimeException("Car not found");
        }

        Car car = carOptional.get();
        car.setStatus(status);
        return carRepository.save(car);
    }

    public List<Car> getFilteredAndSortedCars(String status, String brand, String model, String sortBy) {
        Specification<Car> specification = Specification
                .where(CarSpecifications.withStatus(status))
                .and(CarSpecifications.withModel(model))
                .and(CarSpecifications.withBrand(brand));

        Sort sort = Sort.by(sortBy == null ? "id" : sortBy); // Сортировка по ID, если sortBy не указан

        return carRepository.findAll(specification, sort);
    }

    public void updateRepairStatus(Long carId, boolean inRepair) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (car.getStatus() == Car.CarStatus.RENTED) {
            throw new RuntimeException("Car is rented");
        }

        car.setStatus(inRepair ? Car.CarStatus.IN_REPAIR : Car.CarStatus.AVAILABLE);
        carRepository.save(car);
    }
}
