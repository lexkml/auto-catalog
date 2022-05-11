package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.dto.carDto.request.CarCreateRequest;
import com.kamelchukov.autocatalog.repository.CarRepository;
import com.kamelchukov.autocatalog.repository.FullDataOfCarRepository;
import com.kamelchukov.autocatalog.transformer.CarTransformer;
import com.kamelchukov.common.exception.EntityNotFoundException;
import com.kamelchukov.common.model.dto.carDto.response.FullDataOfCarResponse;
import com.kamelchukov.common.rabbit.RabbitSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    private CarRepository carRepository;
    private FullDataOfCarRepository fullDataOfCarRepository;
    private RabbitSender sender;

    public Car create(CarCreateRequest request) {
        sender.sendMessage();
        return carRepository.save(CarTransformer.fromDto(request));
    }

    public Car findById(Long id) {
        sender.sendMessage();
        return loadById(id);
    }

    public List<Car> findAll() {
        sender.sendMessage();
        List<Car> list = new ArrayList<>();
        carRepository.findAll().forEach(list::add);
        return list;
    }

    public void remove(Long id) {
        sender.sendMessage();
        var removedCar = loadById(id);
        carRepository.delete(removedCar);
    }

    public FullDataOfCarResponse findFullDataOfCarById(Long id) {
        sender.sendMessage();
        return fullDataOfCarRepository.findFullDataOfCarById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Car with id = " + id + " was not found");
                });
    }

    public List<FullDataOfCarResponse> findFullDataOfAllCars() {
        sender.sendMessage();
        return fullDataOfCarRepository.findFullDataOfAllCars();
    }

    public void save(Car car) {
        sender.sendMessage();
        carRepository.save(car);
    }

    private Car loadById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Car with id = " + id + " was not found");
                });
    }
}
