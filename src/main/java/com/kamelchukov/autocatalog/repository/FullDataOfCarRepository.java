package com.kamelchukov.autocatalog.repository;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.Person;
import com.kamelchukov.autocatalog.model.dto.carDto.response.FullDataOfCarResponse;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FullDataOfCarRepository extends CrudRepository<Car, Person> {

    @Query("SELECT c.*, p.first_name, p.last_name FROM catalog.car c " +
            "LEFT JOIN catalog.person p on p.id = c.person_id WHERE c.id = :id")
    Optional<FullDataOfCarResponse> findFullDataOfCarById(@Param("id") Long id);

    @Query("SELECT c.*, p.first_name, p.last_name FROM catalog.car c " +
            "LEFT JOIN catalog.person p on p.id = c.person_id")
    List<FullDataOfCarResponse> findFullDataAllOfCars();

}
