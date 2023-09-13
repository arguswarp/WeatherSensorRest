package com.argus.alishevspring.WeatherSensorRest.services;

import com.argus.alishevspring.WeatherSensorRest.exceptions.SensorNotRegisteredException;
import com.argus.alishevspring.WeatherSensorRest.models.Measurement;
import com.argus.alishevspring.WeatherSensorRest.repositories.MeasurementRepository;
import com.argus.alishevspring.WeatherSensorRest.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setSensor(sensorRepository.findSensorByName(measurement.getSensor().getName()).orElseThrow(SensorNotRegisteredException::new));
        measurement.setCreatedAt(LocalDateTime.now());
        measurementRepository.save(measurement);
    }
}
