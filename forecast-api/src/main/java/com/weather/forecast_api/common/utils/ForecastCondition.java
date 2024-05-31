package com.weather.forecast_api.common.utils;

import com.weather.forecast_api.common.entities.Forecast;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum ForecastCondition {

    VERY_HOT("veryHot", forecast -> forecast.getTemp() > 30),
    RAINY_AND_COLD("rainyAndCold", forecast -> forecast.getTemp() < 10 && forecast.getPrecipitation() > 0.5);


    private static final Map<String,ForecastCondition> CONDITION_NAME_TO_ENUM_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(cond -> cond.getCondName().toLowerCase(), cond -> cond));

    @Getter
    private final String condName;
    private final Predicate<Forecast> condPredicate;

    ForecastCondition(String condName, Predicate<Forecast> condPredicate) {
        this.condName = condName;
        this.condPredicate = condPredicate;
    }

    public boolean checkCondition(Forecast forecast){
        return condPredicate.test(forecast);
    }

    public static ForecastCondition getFromConditionName(String code){
        return CONDITION_NAME_TO_ENUM_MAP.get(code.toLowerCase());
    }
}
