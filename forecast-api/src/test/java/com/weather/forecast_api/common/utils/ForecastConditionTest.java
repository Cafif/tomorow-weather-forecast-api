package com.weather.forecast_api.common.utils;

import com.weather.forecast_api.common.entities.Forecast;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ForecastConditionTest {

    @Test
    public void testCheckConditionVeryHot_rue() {
        Forecast forecast = mock(Forecast.class);
        when(forecast.getTemp()).thenReturn(31.0);

        assertTrue(ForecastCondition.VERY_HOT.checkCondition(forecast));
    }

    @Test
    public void testCheckConditionRainyAndColdTrue() {
        Forecast forecast = mock(Forecast.class);
        when(forecast.getTemp()).thenReturn(5.0);
        when(forecast.getPrecipitation()).thenReturn(0.6);

        assertTrue(ForecastCondition.RAINY_AND_COLD.checkCondition(forecast));
    }

    @Test
    public void testCheckConditionVeryHotFalse() {
        Forecast forecast = mock(Forecast.class);
        when(forecast.getTemp()).thenReturn(30.0);

        assertFalse(ForecastCondition.VERY_HOT.checkCondition(forecast));
    }

    @Test
    public void testCheckConditionRainyAndColdFalse() {
        Forecast forecast = mock(Forecast.class);
        when(forecast.getTemp()).thenReturn(15.0);
        when(forecast.getPrecipitation()).thenReturn(0.3);

        assertFalse(ForecastCondition.RAINY_AND_COLD.checkCondition(forecast));
    }

    @Test
    public void testGetFromConditionNameValidName() {
        assertEquals(ForecastCondition.VERY_HOT, ForecastCondition.getFromConditionName("veryHot"));
    }

    @Test
    public void testGetFromConditionNameInvalidName() {
        assertNull(ForecastCondition.getFromConditionName("invalidName"));
    }
}
