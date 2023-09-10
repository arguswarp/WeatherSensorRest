package com.argus.alishevspring.WeatherSensorRest.repositories;

import com.argus.alishevspring.WeatherSensorRest.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

}
