package com.kamelchukov.autocatalog.repository;

import com.kamelchukov.autocatalog.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Integer> {

    public List <Car> findAll();

    public Car getCarById(int id);

    public void deleteById(int id);


}
