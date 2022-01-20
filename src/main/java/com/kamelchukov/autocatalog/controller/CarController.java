package com.kamelchukov.autocatalog.controller;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.CarCreateRequest;
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

    @PostMapping("/cars")
    @Operation(summary = "Save car")
    public Car save(@RequestBody CarCreateRequest carCreateRequest) {
        return carService.save(carCreateRequest);
    }

    @GetMapping("/cars/{id}")
    @Operation(summary = "Find car by ID")
    public Car findById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @GetMapping("/cars")
    @Operation(summary = "Find all cars")
    public List<Car> findAll() {
        return carService.findAll();
    }

    @PutMapping("/cars/{id}")
    @Operation(summary = "Edit car by ID")
    public Car edit(@PathVariable Long id, @RequestParam int price) {
        return carService.editCarById(id, price);
    }

    @DeleteMapping("/cars/{id}")
    @Operation(summary = "Delete car by ID")
    public Car delete(@PathVariable Long id) {
        return carService.delete(id);
    }
}
