package com.example.autocatalog.service;

import com.example.autocatalog.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CarService {

    private static final Map<Integer, Car> CAR_MAP = new HashMap<>();

    private static final AtomicInteger CAR_ID = new AtomicInteger();


    public void create(Car car) {
        final int id = CAR_ID.incrementAndGet();
        car.setId(id);
        CAR_MAP.put(car.getId(), car);
    }

    public List<Car> readAll() {
        return new ArrayList<>(CAR_MAP.values());
    }

    public Car read(int id) {
        return CAR_MAP.get(id);
    }

    public boolean update(Car car, int id) {
        if (CAR_MAP.containsKey(id)) {
            car.setId(id);
            CAR_MAP.put(id, car);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        if (CAR_MAP.containsKey(id)) {
            CAR_MAP.remove(id);
            return true;
        }
        return false;
    }
}
