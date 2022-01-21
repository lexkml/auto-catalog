package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.request.CarCreateRequest;
import com.kamelchukov.autocatalog.model.dto.carDto.response.CarResponse;
import com.kamelchukov.autocatalog.repository.CarRepository;
import com.kamelchukov.autocatalog.transformer.CarTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    private CarRepository carRepository;

    public Car create(CarCreateRequest request) {
        Car car = CarTransformer.fromDto(request);
        carRepository.save(car);
        return car;
    }

    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Car with id = " + id + " was not found");
                });
    }

    public List<CarResponse> findAll() {
        List<CarResponse> list = new ArrayList<>();
        carRepository.findAll().forEach(car -> list.add(CarTransformer.toResponse(car)));
        return list;
    }

    public void delete(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Car with id = " + id + " was not found");
        }
    }

    //используется для обновления машины, при добавлении и удалении списка машин у пользователя
    public void save(Car car) {
        carRepository.save(car);
    }
}
