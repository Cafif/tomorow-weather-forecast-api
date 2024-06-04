<H2> How To Use This Service </H2>
This service provides an API for weather forecast insights, the service features two endpoints:

1. An endpoint to retrieve insights regarding the forecasts existing in the service's DB.
2. An endpoint to upload a CSV file containing forecasts data - so that they are added to the DB.

<H3>Get Forecast Insights</H3>

To get forecast insights please send a `GET` request to the following URL:

`https://tomorow-weather-forecast.onrender.com/weather/insight`

With the following parameters:

`condition` = veryHot **OR** rainyAndCold
1. `veryHot` - temperature > 30
2. `rainyAndCold` - temperature < 10 AND precipitation > 0.5

`lon = xx.xx`, `lan = yy.yy` - coordinates for the requested insights location.

The service will return a list of **ALL** forecasts available at the exact location, specifying whether or not they met the condition.

For example, for the following request:

`https://tomorow-weather-forecast.onrender.com/weather/insight?lon=51.5&lat=24.5&condition=rainyAndCold`

the service will return this response:
```
[{
  "forecastTime": "2021-04-02T13:00:00Z",
  "conditionMet": false
}, {
  "forecastTime": "2021-04-02T14:00:00Z",
  "conditionMet": false
}, {
  "forecastTime": "2021-04-02T15:00:00Z",
  "conditionMet": true
}]
```

<H3>Upload Forecasts File</H3> 
To upload forecasts to this service, send a `POST` request to the following URL:
`https://tomorow-weather-forecast.onrender.com/weather/upload-csv`

In your request's body include a `'file'` property with a value of a `CSV` file in the following format:

```
Longitude,Latitude,forecast_time,Temperature Celsius,Precipitation Rate mm/hr
-180.0,-90.0,2021-04-02T15:00:00,29.9,13.7
-179.5,-90.0,2021-04-02T15:00:00,-4.2,16.3
-179.0,-90.0,2021-04-02T15:00:00,9.1,5.9
```
The columns may be in any order but the file must include a header row specifying the schema.


<H3>Additional Notes</H3>

* The service is hosted by `Render.com` - Render has a "dimming" mechanism that causes the service to take some time (around a minute) to respond if no requests were sent to it for a while.
* Since the service's DB is running with a free deployment plan (provided by `aiven.com`), uploading large files might take a very (VERY) long time.
