package com.kamelchukov.autocatalog.controller;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.CarCreateRequest;
import com.kamelchukov.autocatalog.model.dto.carDto.CarUpdateRequest;
import com.kamelchukov.autocatalog.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Car controller")
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    @Operation(summary = "Find all cars")
    public List<Car> findAll() {
        List<Car> list = carService.findAll();
        return list;
    }

    @GetMapping("/cars/{id}")
    @Operation(summary = "Find car by ID")
    public Car findById(@PathVariable Long id) {
        return carService.findById(id);
    }


    @PostMapping("/cars")
    @Operation(summary = "Save car")
    public Car save(@RequestBody CarCreateRequest carCreateRequest) {
        Car car = carCreateRequest.dtoToCar();
        carService.save(car);
        return car;
    }

    @PutMapping("/cars/{id}")
    @Operation(summary = "Edit car by ID")
    public Car edit(@PathVariable Long id, @RequestBody CarUpdateRequest carUpdateRequest) {
        Car car = carService.update(id, carUpdateRequest);
        return car;
    }

    @DeleteMapping("/cars/{id}")
    @Operation(summary = "Delete car by ID")
    public String delete(@PathVariable Long id) {
        carService.delete(id);
        return "Car with id = " + id + " was deleted";
    }
}
