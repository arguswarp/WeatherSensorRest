package com.argus.alishevspring.WeatherSensorRest.services;


import com.argus.alishevspring.FirstRestApp.exceptions.PersonNotFoundException;
import com.argus.alishevspring.FirstRestApp.models.Person;
import com.argus.alishevspring.FirstRestApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public SensorService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Page<Person> findAll(Pageable pageable) {
        return peopleRepository.findAll(pageable);
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public void save(Person person) {
        enrichPerson(person);
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdateddAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN"); //can use spring security to put somebody here
    }
}
