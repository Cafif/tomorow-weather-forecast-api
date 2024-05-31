package com.weather.forecast_api.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

import java.util.Comparator;
import java.util.Date;

@Data
@Entity
@Table(name = "forecast")
public class Forecast {
    private String id;
    private String longitude;
    private String latitude;
    private Date forecastTimestamp;
    private double temp;
    private double precipitation;


}
