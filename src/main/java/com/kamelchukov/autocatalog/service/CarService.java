package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.CarCreateRequest;
import com.kamelchukov.autocatalog.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Car save(CarCreateRequest carCreateRequest) {
        Car car = carCreateRequest.dtoToCar();
        carRepository.save(car);
        return car;
    }

    public Car findById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Car with id = " + id + " was not found");
                });
        return car;
    }

    public List<Car> findAll() {
        List<Car> list = new ArrayList<Car>();
        carRepository.findAll().forEach(list::add);
        return list;
    }

    public Car editCarById(Long id, int price) {
        Car car = findById(id);
        car.setPrice(price);
        carRepository.save(car);
        return car;
    }

    public Car delete(Long id) {
        Car car = findById(id);
        carRepository.delete(car);
        return car;
    }
}
