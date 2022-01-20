package com.kamelchukov.autocatalog.repository;

import com.kamelchukov.autocatalog.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}
