package com.kamelchukov.autocatalog.repository;

import com.kamelchukov.autocatalog.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
}
