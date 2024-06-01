package com.weather.forecast_api.common.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Comparator;
import java.util.Date;

@Data
@Entity
@Table(name = "forecast")
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double longitude;
    private double latitude;
    private Date forecastTimestamp;
    private double temp;
    private double precipitation;


}
