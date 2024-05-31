package com.weather.forecast_api.controllers;

import com.weather.forecast_api.common.ForecastRequestData;
import com.weather.forecast_api.common.utils.ForecastCondition;
import com.weather.forecast_api.services.ForecastService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class ForecastController {

    private ForecastService forecastService;
    @PostMapping("/insight?condition={condition}&lat={lat}&lon={lon}")
    public Object handleForecastInsightsRequest(@PathVariable String condition, @PathVariable String lat, @PathVariable String lon){

        ForecastRequestData forecastRequestData = createRequestData(condition,lat, lon);

        return null;
    }

    private ForecastRequestData createRequestData(String condition,String lat, String lon) {
        ForecastRequestData forecastRequestData = new ForecastRequestData();

        ForecastCondition forecastCondition = ForecastCondition.getFromConditionName(condition);
        if(forecastCondition != null ){
            forecastRequestData.setCondition(forecastCondition);
        }
        else{
            throw new IllegalArgumentException(String.format("Condition %s does not exist", condition));
        }

        try
        {
            forecastRequestData.setLatitude( Double.parseDouble(lat));
            forecastRequestData.setLongitude(Double.parseDouble(lon));
        }
        catch (NumberFormatException | NullPointerException e){
            throw new IllegalArgumentException(String.format(String.format("Invalid latitude: %s or longitude %s values, must be a numeric value", lat, lon)));
        }

        return forecastRequestData;
    }
}
