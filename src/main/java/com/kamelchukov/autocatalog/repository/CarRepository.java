package com.kamelchukov.autocatalog.repository;

import com.kamelchukov.autocatalog.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Integer> {

    public List <Car> findAll();

    Car findById(Long id);

    public void deleteById(Long id);

}
