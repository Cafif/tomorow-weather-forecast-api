package com.weather.forecast_api.controllers;

import com.weather.forecast_api.common.InsightsRequestData;
import com.weather.forecast_api.common.utils.ForecastCondition;
import com.weather.forecast_api.services.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class ForecastController {

    @Autowired
    private ForecastService forecastService;


    @GetMapping("/insight?condition={condition}&lat={lat}&lon={lon}")
    public Object handleForecastInsightsRequest(@PathVariable String condition, @PathVariable String lat, @PathVariable String lon){

        InsightsRequestData insightsRequestData = createRequestData(condition,lat, lon);

        return null;
    }

    private InsightsRequestData createRequestData(String condition, String lat, String lon) {
        InsightsRequestData insightsRequestData = new InsightsRequestData();

        ForecastCondition forecastCondition = ForecastCondition.getFromConditionName(condition);
        if(forecastCondition != null ){
            insightsRequestData.setCondition(forecastCondition);
        }
        else{
            throw new IllegalArgumentException(String.format("Condition %s does not exist", condition));
        }

        try
        {
            insightsRequestData.setLatitude( Double.parseDouble(lat));
            insightsRequestData.setLongitude(Double.parseDouble(lon));
        }
        catch (NumberFormatException | NullPointerException e){
            throw new IllegalArgumentException(String.format(String.format("Invalid latitude: %s or longitude %s values, must be a numeric value", lat, lon)));
        }

        return insightsRequestData;
    }
}
