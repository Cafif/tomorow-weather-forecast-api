package com.weather.forecast_api.services;

import com.weather.forecast_api.common.InsightsRequestData;
import com.weather.forecast_api.common.InsightsResult;
import com.weather.forecast_api.common.entities.Forecast;
import com.weather.forecast_api.common.utils.ForecastDateComparator;
import com.weather.forecast_api.common.utils.ForecastInsight;
import com.weather.forecast_api.common.utils.InsightsResultStatus;
import com.weather.forecast_api.repositories.ForecastRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastInsightsService {

    @Autowired
    private ForecastRepository forecastRepository;
    private final ForecastDateComparator forecastDateComparator = new ForecastDateComparator();

    public ForecastInsightsService(ForecastRepository forecastRepository){
        this.forecastRepository = forecastRepository;
    }

    public InsightsResult handleInsightsRequest(InsightsRequestData requestData){

        InsightsResult insightsResult = new InsightsResult();

        List<Forecast> locationMatchingForecasts =  forecastRepository.findByLatitudeAndLongitude(requestData.getLatitude(), requestData.getLongitude());

        if(locationMatchingForecasts.isEmpty()){
            insightsResult.setInsightsResultStatus(InsightsResultStatus.NOT_FOUND);
            return insightsResult;
        }
        List<ForecastInsight> forecastInsights = locationMatchingForecasts.stream()
                .sorted(forecastDateComparator)
                .map(forecast -> new ForecastInsight(forecast, requestData.getCondition().checkCondition(forecast)))
                .toList();

        insightsResult.setForecastInsights(forecastInsights);
        insightsResult.setInsightsResultStatus(InsightsResultStatus.SUCCESS);

        return insightsResult;
    }

}
