package com.weather.forecast_api.common.entities;

import lombok.Getter;

import java.util.Date;

@Getter
public class Forecast {
    private String longitude;
    private String latitude;
    private Date forecastTimestamp;
    private double temp;
    private double precipitation;


}
