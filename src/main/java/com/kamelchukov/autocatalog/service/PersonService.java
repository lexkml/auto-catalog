package com.kamelchukov.autocatalog.service;

import com.kamelchukov.autocatalog.exception.EntityNotFoundException;
import com.kamelchukov.autocatalog.exception.IncorrectDataException;
import com.kamelchukov.autocatalog.model.Car;
import com.kamelchukov.autocatalog.model.Person;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonAddOrDeleteCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonCreateAndDeleteCarsRequest;
import com.kamelchukov.autocatalog.model.dto.personDto.PersonUpdateRequest;
import com.kamelchukov.autocatalog.repository.CarRepository;
import com.kamelchukov.autocatalog.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;

    public void save(Person person) {
        personRepository.save(person);
    }

    public Person update(Long id, PersonUpdateRequest personUpdateRequest) {
        Person person = findById(id);
        person.setFirstName(personUpdateRequest.getFirstName());
        person.setLastName(personUpdateRequest.getLastName());
        return person;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        Person person = personRepository.findById(id);

        if (person == null) {
            throw new EntityNotFoundException("Person with id = " + id + " was not founded");
        }

        return person;
    }

    public void deleteById(Long id) {
        findById(id);
        personRepository.deleteById(id);
    }

    public Person addCarsToPerson(Long personId, PersonAddOrDeleteCarsRequest request) {
        Person person = findById(personId);

        for (Long carId : request.getCars()) {
            Car carForAdded = carRepository.findById(carId);

            if (carForAdded == null) {
                throw new EntityNotFoundException("Car with id = " + carId + " was not founded");
            }

            carForAdded.setPersonId(personId);
            person.getCars().add(carForAdded);
            carRepository.save(carForAdded);
        }

        return person;
    }

    public Person deleteCarsFromPerson(Long personId, PersonAddOrDeleteCarsRequest request) {
        Person person = findById(personId);

        for (Long carId : request.getCars()) {
            Car carForDeleted = carRepository.findById(carId);

            if (carForDeleted == null) {
                throw new EntityNotFoundException("Car with id = " + carId + " was not founded");
            } else if (personId != (carForDeleted.getPersonId())) {
                throw new IncorrectDataException(
                        "Car with id = " + carId + " not owned Person with id = " + personId);
            }

            Set<Car> cars = person.getCars();

            if (cars.contains(carForDeleted)) {
                cars.remove(carForDeleted);
                carForDeleted.setPersonId(null);
                carRepository.save(carForDeleted);
            }
        }
        return person;
    }

    public Person addAndDeleteCarsForPerson(Long personId, PersonCreateAndDeleteCarsRequest request) {
        Person person = findById(personId);

        Set<Car> cars = person.getCars();

        for (Long carId : request.getToDeleteCars()) {
            Car carForDeleted = carRepository.findById(carId);

            if (carForDeleted == null) {
                throw new EntityNotFoundException("Car with id = " + carId + " was not founded");
            } else if (personId != (carForDeleted.getPersonId())) {
                throw new IncorrectDataException(
                        "Car with id = " + carId + " not owned Person with id = " + personId);
            }

            if (cars.contains(carForDeleted)) {
                cars.remove(carForDeleted);
                carForDeleted.setPersonId(null);
                carRepository.save(carForDeleted);
            }
        }

        for (Long carId : request.getToAddCars()) {
            Car carForAdded = carRepository.findById(carId);

            if (carForAdded == null) {
                throw new EntityNotFoundException("Car with id = " + carId + " was not founded");
            }

            if (!cars.contains(carForAdded)) {
                cars.add(carForAdded);
                carForAdded.setPersonId(personId);
                carRepository.save(carForAdded);
            }
        }
        return personRepository.findById(personId);
    }
}
