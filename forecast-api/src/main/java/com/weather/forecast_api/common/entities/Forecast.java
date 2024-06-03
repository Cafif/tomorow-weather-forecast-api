package com.weather.forecast_api.common.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "forecast")
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JsonProperty("Longitude")
    private double longitude;

    @JsonProperty("Latitude")
    private double latitude;

    @JsonProperty("forecast_time")
    private Date forecastTimestamp;

    @JsonProperty("Temperature Celsius")
    private double temp;

    @JsonProperty("Precipitation Rate mm/hr")
    @JsonAlias("Precipitation Rate in/hr")
    private double precipitation;


}
