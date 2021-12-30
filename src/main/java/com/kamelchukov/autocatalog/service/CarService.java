package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        List<Car> list = carRepository.findAll();
        return list;
    }

    public Car getCarById(int id) {
        Car car = carRepository.getCarById(id);
        return car;
    }

    public Car addCar(Car car) {
        carRepository.save(car);
        return car;
    }

    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }
}
