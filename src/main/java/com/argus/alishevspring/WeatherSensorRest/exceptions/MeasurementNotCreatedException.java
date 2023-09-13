package com.argus.alishevspring.WeatherSensorRest.exceptions;

public class MeasurementNotCreatedException extends RuntimeException {
    public MeasurementNotCreatedException(String msg) {
        super(msg);
    }
}
