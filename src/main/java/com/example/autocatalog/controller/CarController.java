package com.example.autocatalog.controller;

import com.example.autocatalog.model.Car;
import com.example.autocatalog.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(value = "/cars")
    public ResponseEntity<?> create(@RequestBody Car car) {
        carService.create(car);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/cars")
    public ResponseEntity<List<Car>> readAll() {
        final List<Car> cars = carService.readAll();

        if (cars != null && !cars.isEmpty()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/cars/{id}")
    public ResponseEntity<Car> read(@PathVariable(name = "id") int id) {
        final Car car = carService.read(id);

        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/cars/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, Car car) {
        final boolean updated = carService.update(car, id);

        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/cars/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = carService.delete(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

}
