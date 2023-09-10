package com.argus.alishevspring.WeatherSensorRest.services;


import com.argus.alishevspring.WeatherSensorRest.exceptions.SensorNotFoundException;
import com.argus.alishevspring.WeatherSensorRest.models.Sensor;
import com.argus.alishevspring.WeatherSensorRest.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }


    public Sensor findOne(int id) {
        return sensorRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    @Transactional
    public void update(int id, Sensor sensor) {
        sensor.setId(id);
        sensorRepository.save(sensor);
    }

    @Transactional
    public void delete(int id) {
        sensorRepository.deleteById(id);
    }

}
