package com.argus.alishevspring.WeatherSensorRest.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {

    @NotNull(message = "Value should not be empty")
    @DecimalMin(value = "-100", message = "Value can't be lower -100")
    @DecimalMax(value = "100", message = "Value can't be greater 100")
    private double value;
    @NotNull(message = "Raining should not be empty")
    private boolean raining;

    @NotNull(message = "Sensor should not be empty")
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
