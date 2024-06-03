package com.weather.forecast_api.services;

import static com.weather.forecast_api.services.ForecastUploadService.TEXT_CSV_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.weather.forecast_api.common.entities.Forecast;
import com.weather.forecast_api.repositories.ForecastRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class ForecastUploadServiceTest {

    private static final String VALID_FILENAME = "test.csv";
    private static final String INVALID_FILENAME = "test.txt";
    private static final String VALID_CSV_CONTENT = "Longitude,Latitude,forecast_time,Temperature Celsius,Precipitation Rate in/hr\n" +
            "-180.0,-90.0,2021-04-02T15:00:00,29.9,13.7\n" +
            "-179.5,-90.0,2021-04-02T15:00:00,-4.2,16.3\n" +
            "-179.0,-90.0,2021-04-02T15:00:00,9.1,5.9";

    private static final String INVALID_CSV_CONTENT = "InvalidHeader1,InvalidHeader2\n" +
            "Value1,Value2\n" +
            "Value3,Value4";

    private final ForecastRepository forecastRepository;
    private final ForecastUploadService forecastUploadService;

    public ForecastUploadServiceTest() {
        forecastRepository = mock(ForecastRepository.class);
        forecastUploadService = new ForecastUploadService(forecastRepository);
    }


    @Test
    void testProcessAndUploadForecastsCSVFileEmptyFile() {
        MultipartFile file = new MockMultipartFile(VALID_FILENAME, new byte[0]);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            forecastUploadService.processAndUploadForecastsCSVFile(file);
        });
        assertEquals("File is missing or empty", exception.getMessage());
        verifyNoInteractions(forecastRepository);
    }

    @Test
    void testProcessAndUploadForecastsCSVFileWrongFileType() {
        MultipartFile file = new MockMultipartFile(INVALID_FILENAME, VALID_FILENAME, "text/plain", "content".getBytes());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            forecastUploadService.processAndUploadForecastsCSVFile(file);
        });
        assertEquals("Incorrect file type. Please upload a CSV file.", exception.getMessage());
        verifyNoInteractions(forecastRepository);
    }

    @Test
    void testProcessAndUploadForecastsCSVFileSuccess() throws IOException {
        String csvContent = VALID_CSV_CONTENT;

        byte[] csvBytes = csvContent.getBytes();

        MultipartFile file = new MockMultipartFile(VALID_FILENAME, VALID_FILENAME, TEXT_CSV_FORMAT, csvBytes);

        when(forecastRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        List<Forecast> inserted = forecastUploadService.processAndUploadForecastsCSVFile(file);

        assertEquals(3, inserted.size());
        verify(forecastRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testProcessAndUploadForecastsCSVFileInvalidHeader() throws IOException {
        byte[] csvBytes = INVALID_CSV_CONTENT.getBytes();
        MultipartFile file = new MockMultipartFile(VALID_FILENAME, VALID_FILENAME, TEXT_CSV_FORMAT, csvBytes);

        assertThrows(UnrecognizedPropertyException.class, () -> {
            forecastUploadService.processAndUploadForecastsCSVFile(file);
        });
    }
}
