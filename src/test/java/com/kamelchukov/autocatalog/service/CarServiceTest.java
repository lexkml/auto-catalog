package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.request.CarCreateRequest;
import com.kamelchukov.autocatalog.repository.CarRepository;
import com.kamelchukov.autocatalog.transformer.CarTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    private static final Car car = Car.builder()
            .id(15L)
            .model("Tesla")
            .classCar('S')
            .color("Black")
            .year("2020")
            .personId(null)
            .build();

    @Test
    void createTest_successfulCase() {
        var request = CarCreateRequest.builder()
                .model(car.getModel())
                .classCar(car.getClassCar())
                .color(car.getColor())
                .year(car.getYear())
                .personId(car.getPersonId())
                .build();

        var resultCar = CarTransformer.fromDto(request);
        resultCar.setId(car.getId());

        when(carRepository.save(any(Car.class))).thenReturn(car);

        carService.create(request);

        assertEquals(car, resultCar);
    }

    @Test
    void findByIdTest_successfulCase() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        var result = carService.findById(anyLong());

        assertEquals(car, result);
    }

    @Test
    void findByIdTest_IfCarNotFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.findById(anyLong()));
    }

    @Test
    void findAllTest_successfulCase() {
        var cars = List.of(car);
        when(carRepository.findAll()).thenReturn(cars);

        var result = carService.findAll();

        assertThat(cars).containsAll(result);
    }

    @Test
    void deleteTest_successfulCase() {
        when(carRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(carRepository).deleteById(anyLong());

        carService.delete(anyLong());

        verify(carRepository).deleteById(anyLong());
    }

    @Test
    void deleteTest_IfCarNotFound() {
        when(carRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> carService.delete(anyLong()));
    }

    @Test
    void saveTest_successfulCase() {
        when(carRepository.save(any(Car.class))).thenReturn(car);

        carService.save(car);

        verify(carRepository).save(any(Car.class));
    }
}