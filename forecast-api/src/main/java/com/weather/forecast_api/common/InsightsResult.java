package com.weather.forecast_api.common;

import com.weather.forecast_api.common.utils.ForecastInsight;
import com.weather.forecast_api.common.utils.InsightsResultStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InsightsResult {
    List<ForecastInsight> forecastInsights;
    InsightsResultStatus insightsResultStatus;

}
