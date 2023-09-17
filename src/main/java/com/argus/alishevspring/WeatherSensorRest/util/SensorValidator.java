package com.argus.alishevspring.WeatherSensorRest.util;

import com.argus.alishevspring.WeatherSensorRest.models.Sensor;
import com.argus.alishevspring.WeatherSensorRest.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        sensorService.findByName(sensor.getName())
                .ifPresent(s -> errors.rejectValue("name", "Sensor with this name already exists"));
    }
}
