package com.kamelchukov.autocatalog.controller;

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

@RequiredArgsConstructor
@RestController
@Tag(name = "Person controller")
public class PersonController {

    private final PersonService personService;

    @PostMapping("/persons")
    @Operation(summary = "Save person")
    public Person save(@RequestBody PersonCreateRequest personCreateRequest) {
        Person person = personCreateRequest.dtoToPerson();
        personService.save(person);
        return person;
    }

    @PutMapping("/person/{id}")
    @Operation(summary = "Edit person by ID")
    public Person update(@PathVariable Long id, @RequestBody PersonUpdateRequest personUpdateRequest) {
        return personService.update(id, personUpdateRequest);
    }

    @GetMapping("/persons")
    @Operation(summary = "Find all persons")
    public List<Person> findAll() {
        List<Person> list = personService.findAll();
        return list;
    }

    @GetMapping("/persons/{id}")
    @Operation(summary = "Find person by ID")
    public Person findById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @DeleteMapping("/persons/{id}")
    @Operation(summary = "Delete person by ID")
    public void deleteById(@PathVariable Long id) {
        personService.deleteById(id);
    }

    @PostMapping("/person/{id}/cars")
    @Operation(summary = "Add cars by IDs for Person by ID")
    public Person addCarsToPerson(@PathVariable Long id, @RequestBody PersonAddOrDeleteCarsRequest request) {
        return personService.addCarsToPerson(id, request);
    }

    @DeleteMapping("/person/{id}/cars")
    @Operation(summary = "Delete cars by IDs for Person by ID")
    public Person deleteCarsFromPerson(@PathVariable Long id, @RequestBody PersonAddOrDeleteCarsRequest request) {
        return personService.deleteCarsFromPerson(id, request);
    }

    @PatchMapping("/person/{id}/cars")
    @Operation(summary = "Add and Delete cars for Person by ID")
    public Person addAndDeleteCarsForPerson(@PathVariable Long id, @RequestBody PersonCreateAndDeleteCarsRequest request) {
        return personService.addAndDeleteCarsForPerson(id, request);
    }
}
