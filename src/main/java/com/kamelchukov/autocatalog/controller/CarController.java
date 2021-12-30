package com.kamelchukov.autocatalog.controller;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        List<Car> list = carService.getAllCars();
        return list;
    }

    @GetMapping("/cars/{id}")
    public Car getCarById(@PathVariable int id) {
        Car car = carService.getCarById(id);
        return car;
    }

    @PostMapping("/cars")
    public Car addCar(@RequestBody Car car) {
        carService.addCar(car);
        return car;
    }

    @PutMapping("/cars")
    public Car changeCar(@RequestBody Car car) {
        carService.addCar(car);
        return car;
    }

    @DeleteMapping("/cars/{id}")
    public String deleteCar(@PathVariable int id) {
        carService.deleteCar(id);
        return "Car with id = " + id + " was deleted";
    }
}
