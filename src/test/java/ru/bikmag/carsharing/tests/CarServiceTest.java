package ru.bikmag.carsharing.tests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.bikmag.carsharing.repositories.CarRepository;
import ru.bikmag.carsharing.services.CarService;

import static org.assertj.core.api.Assertions.assertThat;

public class CarServiceTest {

    private final CarRepository carRepository = Mockito.mock(CarRepository.class);
    private final CarService carService = new CarService(carRepository);

    @Test
    public void testGetAllCars() {
        assertThat(carService.getAllCars()).isEmpty();
    }
}
