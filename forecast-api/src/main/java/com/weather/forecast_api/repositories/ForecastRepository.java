package com.weather.forecast_api.repositories;

import com.weather.forecast_api.common.entities.Forecast;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, String> {

    List<Forecast> findByLatitudeAndLongitude(double latitude, double longitude);
}
