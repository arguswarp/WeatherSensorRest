package com.argus.alishevspring.WeatherSensorRest.controllers;

import com.argus.alishevspring.FirstRestApp.dto.PersonDTO;
import com.argus.alishevspring.FirstRestApp.exceptions.PersonNotCreatedException;
import com.argus.alishevspring.FirstRestApp.exceptions.PersonNotFoundException;
import com.argus.alishevspring.FirstRestApp.models.Person;
import com.argus.alishevspring.FirstRestApp.services.PeopleService;
import com.argus.alishevspring.FirstRestApp.util.PersonErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class SensorController {

    private final PeopleService peopleService;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople() {
        return peopleService.findAll().stream().map(this::convertToPersonDTO).toList(); //Jackson convert this to JSON
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable(name = "id") int id) {
        return convertToPersonDTO(peopleService.findOne(id)); //Jackson convert this to JSON
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMessage.toString());
        }
        peopleService.save(convertToPerson(personDTO));
        // sending http response with empty body and status 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException exception) {
        PersonErrorResponse response = new PersonErrorResponse("Sensor with this id was not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // NOT_FOUND - 404
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException exception) {
        PersonErrorResponse response = new PersonErrorResponse(exception.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // BAD_REQUEST - 400
    }

}
