package com.weather.forecast_api.common.utils;

import com.weather.forecast_api.common.entities.Forecast;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@NoArgsConstructor
public class ForecastDateComparator implements Comparator<Forecast> {

    @Override
    public int compare(Forecast forecast1, Forecast forecast2) {
        return forecast1.getForecastTimestamp().compareTo(forecast2.getForecastTimestamp());
    }
}
