package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.CarUpdateRequest;
import com.kamelchukov.autocatalog.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> findAll() {
        List<Car> list = carRepository.findAll();
        return list;
    }

    public Car findById(Long id) {
        Car car = carRepository.findById(id);
        if (car == null) {
            throw new EntityNotFoundException("Car with id = " + id + " was not founded");
        }
        return car;
    }

    public Car update(Long id, CarUpdateRequest carUpdateRequest) {
        Car car = findById(id);
        car.setPrice(carUpdateRequest.getPrice());
        save(car);
        return car;
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public void delete(Long id) {
        findById(id);
        carRepository.deleteById(id);
    }
}
