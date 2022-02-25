package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.request.CarCreateRequest;
import com.kamelchukov.autocatalog.repository.CarRepository;
import com.kamelchukov.autocatalog.repository.FullDataOfCarRepository;
import com.kamelchukov.autocatalog.transformer.CarTransformer;
import com.kamelchukov.common.exception.EntityNotFoundException;
import com.kamelchukov.common.model.dto.carDto.response.FullDataOfCarResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    private CarRepository carRepository;
    private FullDataOfCarRepository fullDataOfCarRepository;

    public Car create(CarCreateRequest request) {
        return carRepository.save(CarTransformer.fromDto(request));
    }
    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Car with id = " + id + " was not found");
                });
    }

    public List<Car> findAll() {
        List<Car> list = new ArrayList<>();
        carRepository.findAll().forEach(list::add);
        return list;
    }

    public void delete(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Car with id = " + id + " was not found");
        }
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public FullDataOfCarResponse findFullDataOfCarById(Long id) {
        return fullDataOfCarRepository.findFullDataOfCarById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Car with id = " + id + " was not found");
                });
    }

    public List<FullDataOfCarResponse> findFullDataForAllCars() {
        return fullDataOfCarRepository.findFullDataForAllCars();
    }
}
