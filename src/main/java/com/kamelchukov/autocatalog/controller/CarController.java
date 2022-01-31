package com.kamelchukov.autocatalog.controller;

import com.kamelchukov.autocatalog.model.dto.carDto.request.CarCreateRequest;
import com.kamelchukov.autocatalog.model.dto.carDto.response.CarResponse;
import com.kamelchukov.autocatalog.service.CarService;
import com.kamelchukov.autocatalog.transformer.CarTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@Tag(name = "Car controller")
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
    @Operation(summary = "Delete car")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }
}
