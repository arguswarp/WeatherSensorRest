package com.argus.alishevspring.WeatherSensorRest.controllers;


import com.argus.alishevspring.WeatherSensorRest.dto.SensorDTO;
import com.argus.alishevspring.WeatherSensorRest.exceptions.SensorNotCreatedException;
import com.argus.alishevspring.WeatherSensorRest.exceptions.SensorNotFoundException;
import com.argus.alishevspring.WeatherSensorRest.models.Sensor;
import com.argus.alishevspring.WeatherSensorRest.services.SensorService;
import com.argus.alishevspring.WeatherSensorRest.util.ErrorResponse;
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
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
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
            throw new SensorNotCreatedException(errorMessage.toString());
        }
        sensorService.save(convertToSensor(sensorDTO));
        // sending http response with empty body and status 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotFoundException exception) {
        ErrorResponse response = new ErrorResponse("Sensor with this id was not found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // NOT_FOUND - 404
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotCreatedException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // BAD_REQUEST - 400
    }
}
