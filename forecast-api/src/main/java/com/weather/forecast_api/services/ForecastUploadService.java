package com.weather.forecast_api.services;

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
import java.util.Objects;

@Service
public class ForecastUploadService {

    public static final String TEXT_CSV_FORMAT = "text/csv";
    @Autowired
    ForecastRepository forecastRepository;

    public ForecastUploadService(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    public List<Forecast> processAndUploadForecastsCSVFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is missing or empty");
        }
        if (!Objects.equals(file.getContentType(), TEXT_CSV_FORMAT)) {
            throw new IllegalArgumentException("Incorrect file type. Please upload a CSV file.");
        }

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        ObjectReader objectReader = csvMapper.readerFor(Forecast.class).with(schema);
        List<Forecast> forecasts = objectReader.<Forecast>readValues(file.getInputStream()).readAll();

        List<Forecast> inserted = forecastRepository.saveAll(forecasts);
        return inserted;
    }
}
