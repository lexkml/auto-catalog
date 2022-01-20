package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.exception.IncorrectDataException;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.Person;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonAddOrDeleteCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonCreateAndDeleteCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonCreateRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonUpdateRequest;
import com.kamelchukov.autocatalog.repository.CarRepository;
import com.kamelchukov.autocatalog.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;
    private final CarService carService;

    public Person save(PersonCreateRequest personCreateRequest) {
        Person person = personCreateRequest.dtoToPerson();
        personRepository.save(person);
        return person;
    }

    public Person findById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Person with id = " + id + " was not founded");
                });
    }

    public List<Person> findAll() {
        List<Person> list = new ArrayList<>();
        personRepository.findAll().forEach(list::add);
        return list;
    }

    public Person editById(Long id, PersonUpdateRequest personUpdateRequest) {
        Person person = findById(id);
        person.setFirstName(personUpdateRequest.getFirstName());
        person.setLastName(personUpdateRequest.getLastName());
        return person;
    }

    public Person deleteById(Long id) {
        Person person = findById(id);
        personRepository.delete(person);
        return person;
    }

    public Person addCarsToPerson(Long personId, PersonAddOrDeleteCarsRequest request) {
        Person person = findById(personId);

        for (Long carId : request.getCars()) {
            Car carForAdded = carService.findById(carId);

            if (carForAdded == null) {
                throw new EntityNotFoundException("Car with id = " + carId + " was not founded");
            }

            carForAdded.setPersonId(personId);
            carRepository.save(carForAdded);
        }

        return person;
    }

    public List<Car> deleteCarsFromPerson(Long personId, PersonAddOrDeleteCarsRequest request) {
        Person person = findById(personId);
        List<Car> deletedCars = new ArrayList<>();

        for (Long carId : request.getCars()) {
            Car carForDeleted = carService.findById(carId);

            if (carForDeleted == null) {
                throw new EntityNotFoundException("Car with id = " + carId + " was not founded");
            } else if (!personId.equals(carForDeleted.getPersonId())) {
                throw new IncorrectDataException("Car with id = " + carId + " not owned Person with id = " + personId);
            }

            Set<Car> cars = person.getCars();

            if (cars.contains(carForDeleted)) {
                carForDeleted.setPersonId(null);
                carRepository.save(carForDeleted);

                deletedCars.add(carForDeleted);
            }
        }
        return deletedCars;
    }

    public Map<Car, String> addAndDeleteCarsForPerson(Long personId, PersonCreateAndDeleteCarsRequest request) {
        Person person = findById(personId);
        Set<Car> cars = person.getCars();
        Map<Car, String> addedAndDeletedCars = new HashMap<>();

        for (Long carId : request.getToDeleteCars()) {
            Car carForDeleted = carService.findById(carId);

            if (carForDeleted == null) {
                throw new EntityNotFoundException("Car with id = " + carId + " was not founded");
            } else if (!personId.equals(carForDeleted.getPersonId())) {
                throw new IncorrectDataException("Car with id = " + carId + " not owned Person with id = " + personId);
            }

            if (cars.contains(carForDeleted)) {
                carForDeleted.setPersonId(null);
                carRepository.save(carForDeleted);
                addedAndDeletedCars.put(carForDeleted, "Deleted");
            }
        }

        for (Long carId : request.getToAddCars()) {
            Car carForAdded = carService.findById(carId);

            if (carForAdded == null) {
                throw new EntityNotFoundException("Car with id = " + carId + " was not founded");
            } else if (carForAdded.getPersonId() != null) {
                throw new IncorrectDataException("Car with id = " + carId + " owned Person with id = " + carForAdded.getPersonId());
            }

            if (!cars.contains(carForAdded)) {
                carForAdded.setPersonId(personId);
                carRepository.save(carForAdded);
                addedAndDeletedCars.put(carForAdded, "Added");
            }
        }
        return addedAndDeletedCars;
    }
}
