package com.weather.forecast_api.services;

import com.weather.forecast_api.common.InsightsRequestData;
import com.weather.forecast_api.common.InsightsResult;
import com.weather.forecast_api.common.entities.Forecast;
import com.weather.forecast_api.common.utils.ForecastCondition;
import com.weather.forecast_api.common.utils.ForecastInsight;
import com.weather.forecast_api.common.utils.InsightsResultStatus;
import com.weather.forecast_api.repositories.ForecastRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ForecastInsightsServiceTest {

    private ForecastRepository forecastRepository;
    private ForecastInsightsService forecastInsightsService;

    public ForecastInsightsServiceTest() {
        forecastRepository = mock(ForecastRepository.class);
        forecastInsightsService = new ForecastInsightsService(forecastRepository);
    }

    @Test
    public void testHandleInsightsRequestNotFound() {
        when(forecastRepository.findByLatitudeAndLongitude(anyDouble(), anyDouble())).thenReturn(new ArrayList<>());

        InsightsRequestData requestData = new InsightsRequestData();
        requestData.setLatitude(10.0);
        requestData.setLongitude(20.0);
        requestData.setCondition(ForecastCondition.VERY_HOT);

        InsightsResult result = forecastInsightsService.handleInsightsRequest(requestData);

        assertEquals(InsightsResultStatus.NOT_FOUND, result.getInsightsResultStatus());
        assertNull(result.getForecastInsights());
    }

    @Test
    public void testHandleInsightsRequestSuccess() {
        List<Forecast> forecasts = new ArrayList<>();
        Forecast forecast1 = new Forecast();
        forecasts.add(forecast1);
        when(forecastRepository.findByLatitudeAndLongitude(anyDouble(), anyDouble())).thenReturn(forecasts);

        InsightsRequestData requestData = new InsightsRequestData();
        requestData.setLatitude(10.0);
        requestData.setLongitude(20.0);
        requestData.setCondition(ForecastCondition.VERY_HOT);

        InsightsResult result = forecastInsightsService.handleInsightsRequest(requestData);

        assertEquals(InsightsResultStatus.SUCCESS, result.getInsightsResultStatus());
        assertTrue(result.getForecastInsights().stream().map(ForecastInsight::getForecast).collect(Collectors.toSet()).contains(forecast1));
    }
}
