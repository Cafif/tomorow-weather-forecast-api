package com.weather.forecast_api.controllers;

import com.weather.forecast_api.common.InsightsRequestData;
import com.weather.forecast_api.common.InsightsResult;
import com.weather.forecast_api.common.utils.ForecastCondition;
import com.weather.forecast_api.common.utils.ForecastInsight;
import com.weather.forecast_api.common.utils.InsightsResultStatus;
import com.weather.forecast_api.services.ForecastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class ForecastController {

    private static final Logger log = LoggerFactory.getLogger(ForecastController.class);
    @Autowired
    private ForecastService forecastService;


    @GetMapping("/insight")
    public ResponseEntity<String> handleForecastInsightsRequest(@RequestParam String condition, @RequestParam String lat, @RequestParam String lon) {
        try {
            InsightsRequestData insightsRequestData = createRequestData(condition, lat, lon);

            InsightsResult insightsResult = forecastService.handleInsightsRequest(insightsRequestData);

            switch (insightsResult.getInsightsResultStatus()){
                case SUCCESS:
                    return ResponseEntity.ok(insightsResult.getForecastInsights().toString());
                case NOT_FOUND:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Forecasts not found for the requested coordinates");
                case ERROR:
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
            }



        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");

        }
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
            throw new IllegalArgumentException(String.format(String.format("Invalid latitude:%s or longitude:%s values, must be a numeric value", lat, lon)));
        }

        return insightsRequestData;
    }
}
