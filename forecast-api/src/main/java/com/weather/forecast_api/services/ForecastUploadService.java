package com.weather.forecast_api.services;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.weather.forecast_api.common.entities.Forecast;
import com.weather.forecast_api.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ForecastUploadService {

    @Autowired
    ForecastRepository forecastRepository;
    ObjectReader forecastIter = new CsvMapper().readerWithTypedSchemaFor(Forecast.class);;

    public void processCSVFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        ObjectReader objectReader = csvMapper.readerFor(Forecast.class).with(schema);
        List<Forecast> forecasts = objectReader.<Forecast>readValues(file.getInputStream()).readAll();

        forecastRepository.saveAll(forecasts);
    }
}
