package com.kamelchukov.autocatalog.service;

import com.common.exception.EntityNotFoundException;
import com.common.model.dto.carDto.response.FullDataOfCarResponse;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.request.CarCreateRequest;
import com.kamelchukov.autocatalog.repository.CarRepository;
import com.kamelchukov.autocatalog.repository.FullDataOfCarRepository;
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

    @Mock
    private FullDataOfCarRepository fullDataOfCarRepository;

    private static final Car CAR = Car.builder()
            .id(15L)
            .model("Tesla")
            .color("Black")
            .year("2020")
            .personId(null)
            .build();

    private static final FullDataOfCarResponse FULL_DATA_OF_CAR = FullDataOfCarResponse.builder()
            .id(15L)
            .model("Tesla")
            .color("Black")
            .year("2020")
            .personId(5L)
            .firstName("Ivan")
            .lastName("Ivanov")
            .build();

    @Test
    void createTest_successfulCase() {
        var request = CarCreateRequest.builder()
                .model(CAR.getModel())
                .color(CAR.getColor())
                .year(CAR.getYear())
                .personId(CAR.getPersonId())
                .build();

        var resultCar = CarTransformer.fromDto(request);
        resultCar.setId(CAR.getId());

        when(carRepository.save(any(Car.class))).thenReturn(CAR);

        carService.create(request);

        assertEquals(CAR, resultCar);
    }

    @Test
    void findByIdTest_successfulCase() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(CAR));

        var result = carService.findById(anyLong());

        assertEquals(CAR, result);
    }

    @Test
    void findByIdTest_IfCarNotFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.findById(anyLong()));
    }

    @Test
    void findAllTest_successfulCase() {
        var cars = List.of(CAR);
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
        when(carRepository.save(any(Car.class))).thenReturn(CAR);

        carService.save(CAR);

        verify(carRepository).save(any(Car.class));
    }

    @Test
    void findFullDataOfCarByIdTest_successfulCase() {
        when(fullDataOfCarRepository.findFullDataOfCarById(anyLong())).thenReturn(Optional.of(FULL_DATA_OF_CAR));

        var result = carService.findFullDataOfCarById(anyLong());

        assertEquals(FULL_DATA_OF_CAR, result);
    }

    @Test
    void findFullDataOfCarByIdTest_ifCarNotFound() {
        when(fullDataOfCarRepository.findFullDataOfCarById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.findFullDataOfCarById(anyLong()));
    }

    @Test
    void findFullDataAllOfCarsTest_successfulCase() {
        var list = List.of(FULL_DATA_OF_CAR);
        when(fullDataOfCarRepository.findFullDataForAllCars()).thenReturn(list);

        var result = carService.findFullDataForAllCars();

        assertThat(result).containsAll(list);
    }
}