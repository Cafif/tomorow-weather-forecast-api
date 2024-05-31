package com.weather.forecast_api.controllers;

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


        return null;
    }
}
