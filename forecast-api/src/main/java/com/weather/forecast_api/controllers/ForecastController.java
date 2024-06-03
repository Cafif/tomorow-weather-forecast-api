package com.weather.forecast_api.controllers;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.weather.forecast_api.common.InsightsRequestData;
import com.weather.forecast_api.common.InsightsResult;
import com.weather.forecast_api.common.entities.Forecast;
import com.weather.forecast_api.common.utils.ForecastCondition;
import com.weather.forecast_api.services.ForecastInsightsService;
import com.weather.forecast_api.services.ForecastUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class ForecastController {

    @Autowired
    private ForecastInsightsService forecastInsightsService;
    @Autowired
    private ForecastUploadService forecastUploadService;


    @GetMapping("/insight")
    public ResponseEntity<String> getForecastInsights(@RequestParam String condition, @RequestParam String lat, @RequestParam String lon) {
        try {
            InsightsRequestData insightsRequestData = createRequestData(condition, lat, lon);

            InsightsResult insightsResult = forecastInsightsService.handleInsightsRequest(insightsRequestData);

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
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");

        }
    }


    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadForecastsCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<Forecast> addedForecasts = forecastUploadService.processAndUploadForecastsCSVFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(String.format("CSV File uploaded successfully, %s forecasts added",addedForecasts.size()));
        }


        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (UnrecognizedPropertyException e) {
            return ResponseEntity.badRequest().body("Invalid CSV file format, file is missing or has an unrecognized header row");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. Failed to upload CSV file");
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
