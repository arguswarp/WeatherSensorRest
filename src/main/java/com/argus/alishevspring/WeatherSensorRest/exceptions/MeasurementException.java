package com.argus.alishevspring.WeatherSensorRest.exceptions;

public class MeasurementException extends RuntimeException{
    public MeasurementException(String msg) {
        super(msg);
    }
}
