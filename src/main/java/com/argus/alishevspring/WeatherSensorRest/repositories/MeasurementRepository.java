package com.argus.alishevspring.WeatherSensorRest.repositories;

import com.argus.alishevspring.WeatherSensorRest.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
