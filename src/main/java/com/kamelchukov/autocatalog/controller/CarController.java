package com.kamelchukov.autocatalog.controller;

import com.kamelchukov.autocatalog.model.dto.carDto.request.CarCreateRequest;
import com.kamelchukov.autocatalog.model.dto.carDto.response.CarResponse;
import com.kamelchukov.autocatalog.service.CarService;
import com.kamelchukov.autocatalog.transformer.CarTransformer;
import com.kamelchukov.common.model.dto.carDto.response.FullDataOfCarResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Car controller")
@AllArgsConstructor
public class CarController {

    private CarService carService;

    @PostMapping("/cars")
    @Operation(summary = "Create car")
    public CarResponse create(@RequestBody CarCreateRequest carCreateRequest) {
        return CarTransformer.toResponse(carService.create(carCreateRequest));
    }

    @GetMapping("/cars/{id}")
    @Operation(summary = "Find car by ID")
    public CarResponse findById(@PathVariable Long id) {
        return CarTransformer.toResponse(carService.findById(id));
    }

    @GetMapping("/cars")
    @Operation(summary = "Find all cars")
    public List<CarResponse> findAll() {
        return carService.findAll().stream().map(CarTransformer::toResponse).collect(Collectors.toList());
    }

    @DeleteMapping("/cars/{id}")
    @Operation(summary = "Remove car")
    public void remove(@PathVariable Long id) {
        carService.remove(id);
    }

    @GetMapping(value = "/cars/{id}/fullData")
    @Operation(summary = "Find full data of car by ID")
    public FullDataOfCarResponse findFullDataOfCarById(@PathVariable Long id) {
        return carService.findFullDataOfCarById(id);
    }

    @GetMapping("/cars/fullData")
    @Operation(summary = "Find full data for all cars")
    public List<FullDataOfCarResponse> findFullDataOfAllCars() {
        return carService.findFullDataOfAllCars();
    }
}
