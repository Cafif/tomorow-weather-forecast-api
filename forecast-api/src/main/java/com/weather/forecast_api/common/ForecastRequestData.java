package com.weather.forecast_api.common;


import com.weather.forecast_api.common.utils.ForecastCondition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForecastRequestData {

    private ForecastCondition condition;
    private double latitude;
    private double longitude;

}
