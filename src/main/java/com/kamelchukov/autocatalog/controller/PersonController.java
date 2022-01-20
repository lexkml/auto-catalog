package com.kamelchukov.autocatalog.controller;

import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.Person;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonAddOrDeleteCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonCreateAndDeleteCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonCreateRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonUpdateRequest;
import com.kamelchukov.autocatalog.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Tag(name = "Person controller")
public class PersonController {

    private final PersonService personService;

    @PostMapping("/persons")
    @Operation(summary = "Save person")
    public Person save(@RequestBody PersonCreateRequest personCreateRequest) {
        return personService.save(personCreateRequest);
    }

    @GetMapping("/persons/{id}")
    @Operation(summary = "Find person by ID")
    public Person findById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @GetMapping("/persons")
    @Operation(summary = "Find all persons")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PutMapping("/person/{id}")
    @Operation(summary = "Edit person by ID")
    public Person editById(@PathVariable Long id, @RequestBody PersonUpdateRequest personUpdateRequest) {
        return personService.editById(id, personUpdateRequest);
    }

    @DeleteMapping("/persons/{id}")
    @Operation(summary = "Delete person by ID")
    public Person deleteById(@PathVariable Long id) {
       return personService.deleteById(id);
    }

    @PostMapping("/person/{id}/cars")
    @Operation(summary = "Add cars by IDs for Person by ID")
    public Person addCarsToPerson(@PathVariable Long id, @RequestBody PersonAddOrDeleteCarsRequest request) {
        return personService.addCarsToPerson(id, request);
    }

    @DeleteMapping("/person/{id}/cars")
    @Operation(summary = "Delete cars by IDs for Person by ID")
    public List<Car> deleteCarsFromPerson(@PathVariable Long id, @RequestBody PersonAddOrDeleteCarsRequest request) {
        return personService.deleteCarsFromPerson(id, request);
    }

    @PatchMapping("/person/{id}/cars")
    @Operation(summary = "Add and Delete cars for Person by ID")
    public Map<Car, String> addAndDeleteCarsForPerson(@PathVariable Long id, @RequestBody PersonCreateAndDeleteCarsRequest request) {
        return personService.addAndDeleteCarsForPerson(id, request);
    }
}
