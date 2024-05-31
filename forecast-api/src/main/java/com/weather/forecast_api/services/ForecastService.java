package com.weather.forecast_api.services;

import com.weather.forecast_api.common.InsightsRequestData;
import com.weather.forecast_api.common.InsightsResult;
import com.weather.forecast_api.common.entities.Forecast;
import com.weather.forecast_api.common.utils.ForecastDateComparator;
import com.weather.forecast_api.common.utils.ForecastInsight;
import com.weather.forecast_api.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForecastService {

    @Autowired
    private ForecastRepository forecastRepository;
    private final ForecastDateComparator forecastDateComparator = new ForecastDateComparator();

    public InsightsResult handleInsightsRequest(InsightsRequestData requestData){

        InsightsResult insightsResult = new InsightsResult();

        List<Forecast> matchingForecasts =  forecastRepository.findByLatitudeAndLongitude(requestData.getLatitude(), requestData.getLongitude());

        List<ForecastInsight> forecastInsights = matchingForecasts.stream()
                .sorted(forecastDateComparator)
                .map(forecast -> new ForecastInsight(forecast, requestData.getCondition().checkCondition(forecast)))
                .toList();

        insightsResult.setForecastInsights(forecastInsights);

        return insightsResult;
    }

}
