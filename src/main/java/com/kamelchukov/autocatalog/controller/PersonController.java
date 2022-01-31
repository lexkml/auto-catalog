package com.kamelchukov.autocatalog.controller;

import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonAddAndRemoveCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonCreateRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.response.PersonResponse;
import com.kamelchukov.autocatalog.service.PersonService;
import com.kamelchukov.autocatalog.transformer.PersonTransformer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@Tag(name = "Person controller")
public class PersonController {

    private PersonService personService;

    @PostMapping("/persons")
    @Operation(summary = "Create person")
    public PersonResponse create(@RequestBody PersonCreateRequest request) {
        return PersonTransformer.toResponse(personService.create(request));
    }

    @GetMapping("/persons/{id}")
    @Operation(summary = "Find person by ID")
    public PersonResponse findById(@PathVariable Long id) {
        return PersonTransformer.toResponse(personService.findById(id));
    }

    @GetMapping("/persons")
    @Operation(summary = "Find all persons")
    public List<PersonResponse> findAll() {
        return personService.findAll().stream().map(PersonTransformer::toResponse).collect(Collectors.toList());
    }

    @DeleteMapping("/persons/{id}")
    @Operation(summary = "Delete person")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }

    @PostMapping("/person/{id}/cars")
    @Operation(summary = "Add cars by IDs for Person by ID")
    public PersonResponse addCarsToPerson(@PathVariable Long id, @RequestBody Set<Long> carsId) {
        return personService.addCarsToPerson(id, carsId);
    }

    @DeleteMapping("/person/{id}/cars")
    @Operation(summary = "Remove cars by IDs from Person by ID")
    public PersonResponse removeCarsFromPerson(@PathVariable Long id, @RequestBody Set<Long> carsId) {
        return personService.removeCarsFromPerson(id, carsId);
    }

    @PatchMapping("/person/{id}/cars")
    @Operation(summary = "Add and Remove cars from Person by ID")
    public PersonResponse addAndRemoveCarsFromPerson(@PathVariable Long id, @RequestBody PersonAddAndRemoveCarsRequest request) {
        return personService.addAndRemoveCarsFromPerson(id, request);
    }
}
