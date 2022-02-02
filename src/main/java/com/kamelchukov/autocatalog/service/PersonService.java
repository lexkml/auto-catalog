package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.exception.IncorrectDataException;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.Person;
import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonAddAndRemoveCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.request.PersonCreateRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.response.PersonResponse;
import com.kamelchukov.autocatalog.repository.PersonRepository;
import com.kamelchukov.autocatalog.transformer.PersonTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;
    private CarService carService;

    public Person create(PersonCreateRequest request) {
        return personRepository.save(PersonTransformer.fromDto(request));
    }

    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Person with id = " + id + " was not found");
                });
    }

    public List<Person> findAll() {
        List<Person> list = new ArrayList<>();
        personRepository.findAll().forEach(list::add);
        return list;
    }

    public void delete(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Person with id = " + id + " was not found");
        }
    }

    public PersonResponse addCarsToPerson(Long personId, Set<Long> carsId) {
        for (Long carId : carsId) {
            Car carToAdd = carService.findById(carId);
            if (carToAdd.getPersonId() != null) {
                throw new IncorrectDataException(
                        "Car with id = " + carId + " owned Person with id = " + carToAdd.getPersonId());
            } else {
                carToAdd.setPersonId(personId);
                carService.save(carToAdd);
            }
        }
        return PersonTransformer.toResponse(findById(personId));
    }

    public PersonResponse removeCarsFromPerson(Long personId, Set<Long> carsId) {
        for (Long carId : carsId) {
            Car carToRemove = carService.findById(carId);
            if (carToRemove.getPersonId() == null || !carToRemove.getPersonId().equals(personId)) {
                throw new IncorrectDataException("Car with id = " + carId + " not owned this Person!");
            } else {
                carToRemove.setPersonId(null);
                carService.save(carToRemove);
            }
        }
        return PersonTransformer.toResponse(findById(personId));
    }

    public PersonResponse addAndRemoveCarsFromPerson(Long personId, PersonAddAndRemoveCarsRequest request) {
        removeCarsFromPerson(personId, request.getCarsToRemove());
        addCarsToPerson(personId, request.getCarsToAdd());
        return PersonTransformer.toResponse(findById(personId));
    }
}
