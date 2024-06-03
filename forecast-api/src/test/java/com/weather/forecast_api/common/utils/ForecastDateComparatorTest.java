package com.weather.forecast_api.common.utils;

import com.weather.forecast_api.common.entities.Forecast;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class ForecastDateComparatorTest {

    private static final String DATE_1 = "2022-01-01 12:00:00";
    private static final String DATE_2 = "2022-01-02 12:00:00";

    @Test
    public void testCompare_LessThan() {
        Forecast forecast1 = new Forecast();
        forecast1.setForecastTimestamp(Timestamp.valueOf(DATE_1));
        Forecast forecast2 = new Forecast();
        forecast2.setForecastTimestamp(Timestamp.valueOf(DATE_2));

        ForecastDateComparator comparator = new ForecastDateComparator();

        assertTrue(comparator.compare(forecast1, forecast2) < 0);
    }

    @Test
    public void testCompare_GreaterThan() {
        Forecast forecast1 = new Forecast();
        forecast1.setForecastTimestamp(Timestamp.valueOf(DATE_2));
        Forecast forecast2 = new Forecast();
        forecast2.setForecastTimestamp(Timestamp.valueOf(DATE_1));

        ForecastDateComparator comparator = new ForecastDateComparator();

        assertTrue(comparator.compare(forecast1, forecast2) > 0);
    }

    @Test
    public void testCompare_Equal() {
        Forecast forecast1 = new Forecast();
        forecast1.setForecastTimestamp(Timestamp.valueOf(DATE_1));
        Forecast forecast2 = new Forecast();
        forecast2.setForecastTimestamp(Timestamp.valueOf(DATE_1));

        ForecastDateComparator comparator = new ForecastDateComparator();

        assertEquals(0, comparator.compare(forecast1, forecast2));
    }
}
