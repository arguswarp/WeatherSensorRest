package com.argus.alishevspring.WeatherSensorRest.controllers;


import com.argus.alishevspring.WeatherSensorRest.dto.MeasurementDTO;
import com.argus.alishevspring.WeatherSensorRest.dto.MeasurementsResponse;
import com.argus.alishevspring.WeatherSensorRest.exceptions.MeasurementException;
import com.argus.alishevspring.WeatherSensorRest.models.Measurement;
import com.argus.alishevspring.WeatherSensorRest.services.MeasurementService;
import com.argus.alishevspring.WeatherSensorRest.util.ErrorResponse;
import com.argus.alishevspring.WeatherSensorRest.util.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.argus.alishevspring.WeatherSensorRest.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;

    private final ModelMapper modelMapper;

    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public MeasurementsResponse getMeasurements() {
        return new MeasurementsResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO).toList()); //Jackson convert this to JSON
    }

    @GetMapping("/rainyDayCount")
    public Long getRainyDayCount() {
        return measurementService.getRainyDays();
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurementToAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        measurementService.save(measurementToAdd);
        // sending http response with empty body and status 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // BAD_REQUEST - 400
    }
}
