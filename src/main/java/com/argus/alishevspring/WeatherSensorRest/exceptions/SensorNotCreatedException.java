package com.argus.alishevspring.WeatherSensorRest.exceptions;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException(String msg) {
        super(msg);
    }
}
