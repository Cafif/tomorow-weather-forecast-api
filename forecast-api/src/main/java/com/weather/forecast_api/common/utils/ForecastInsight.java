package com.weather.forecast_api.common.utils;

import com.weather.forecast_api.common.entities.Forecast;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForecastInsight {
    private Forecast forecast;
    private boolean matchesCondition;
}
