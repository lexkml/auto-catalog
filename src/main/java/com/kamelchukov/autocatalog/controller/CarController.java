package com.kamelchukov.autocatalog.controller;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Car controller")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    @Operation(summary = "Get all cars")
    public List<Car> getAllCars() {
        List<Car> list = carService.getAllCars();
        return list;
    }

    @GetMapping("/cars/{id}")
    @Operation(summary = "Get car by ID")
    public Car getCarById(@PathVariable int id) {
        Car car = carService.getCarById(id);
        return car;
    }

    @PostMapping("/cars")
    @Operation(summary = "Add car")
    public Car addCar(@RequestBody Car car) {
        carService.addCar(car);
        return car;
    }

    @PutMapping("/cars")
    @Operation(summary = "Change car")
    public Car changeCar(@RequestBody Car car) {
        carService.addCar(car);
        return car;
    }

    @DeleteMapping("/cars/{id}")
    @Operation(summary = "Delete car by ID")
    public String deleteCar(@PathVariable int id) {
        carService.deleteCar(id);
        return "Car with id = " + id + " was deleted";
    }
}
