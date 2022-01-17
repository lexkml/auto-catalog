package com.kamelchukov.autocatalog.repository;

import com.kamelchukov.autocatalog.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    Person findById(Long id);

    public List<Person> findAll();

    public void deleteById(Long id);

}
