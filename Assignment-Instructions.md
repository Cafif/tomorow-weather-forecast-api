# backend-wdata-code-challenge - Weather Data Team Candidate Home Work

## Abstract

Let's build a weather service. In this task, you will digest the weather data source and expose it via an amazing API that you are going to build!

### CSV Files

For this task, we will use CSV files as our data source just to make it simple. In the "real world", we won't use CSV, but other formats that are more optimized for weather data such as NetCDF, Zarr. Attached you will find 3 files in CSV format. The files represent weather forecast data. The files are world coverage so you will be able to know what is the weather all over the world! Each file represents a weather forecast of a different time (day/hour).

**BTW**: this is just mock data, don't try to plan your next trip based on this forecast :)

## The Task

In this task, we want you to create a web service that will give the user a timeline of a weather insight - if a certain condition, based on the weather parameters, is met for a specific location.
To do so, you will have to ingest several files containing weather data into a database of you choice. Each file has 5 columns: Longitude, Latitude, forecast time, Temperature, Precipitation.
The service will expose a single route:

1. `/weather/insight?condition={condition}&lat={lat}&lon={lon}` - will return either `true` or `false` for every timestamp, for two predefined conditions:
    1. `veryHot`  - based on the condition `temperature > 30`
    2. `rainyAndCold` - based on the condition `temperature < 10 AND precipitation > 0.5`

**Please note that Temperature is in Celsius, and Precipitation Rate is in mm/hr.

### Service Explanation

### Route: `/weather/insight`

- **Query Parameters**: lat, lon, condition
    - `condition` can be either `veryHot` or `rainyAndCold`
- **Output (JSON) - Example**:
    
    ```json
    [
      {
        "forecastTime": "2018-12-10T13:00:00.000Z",
        "conditionMet": true,
      },
      {
        "forecastTime": "2018-12-10T14:00:00.000Z",
        "conditionMet": false,
      },
      {
        "forecastTime": "2018-12-10T15:00:00.000Z",
        "conditionMet": false,
      },
    ]
    
    ```
    

## What is Expected

- A working end to end service.
- A github repository with your solution.
- Add a section to your readme: `How to use this service` for example `http://my-cool-service.com/weather/insight?condition=veryHotlat=42.332&lon=35.421` **Make sure that the URL that you are providing is WORKING!** (`curl <YOUR_URŁ>` should provide a result)

### Implementation

The service should have 2 parts:

1. Ingest the CSV into Database.
2. Web Server that reads the data from the Database and displays it.

### Tips

- **Programming Language**: Pick whatever you like, pick the language that you are most comfortable with.
- **Database**: SQL/NoSQL, whatever you think will be fast and good for this task.
- You can use any free hosting provider such as [render.com](http://render.com/), Heroku, AWS, GCS, Azure, DigitalOcean, etc... (if you don't know any of them, we recommend using [render.com](http://render.com/) as it is the easiest to start with and we tested that the task can run on it).
- Take your time to go over the instructions and attached data before you begin.
- You can use the following examples to validate that your code is working as expected:
    - **Request**: `http://{url}/weather/insight?lon=51.5&lat=24.5&condition=veryHot`
      
      **Response**:

      ```json
      [
          {
              "forecastTime": "2021-04-02T13:00:00Z",
              "conditionMet": true
          },
          {
              "forecastTime": "2021-04-02T14:00:00Z",
              "conditionMet": true
          },
          {
              "forecastTime": "2021-04-02T15:00:00Z",
              "conditionMet": false
          }
      ]
      ```
  
    - **Request**: `http://{url}/weather/insight?lon=51.5&lat=24.5&condition=rainyAndCold`
      
      **Response**:
      
      ```json
      [
          {
              "forecastTime": "2021-04-02T13:00:00Z",
              "conditionMet": false
          },
          {
              "forecastTime": "2021-04-02T14:00:00Z",
              "conditionMet": false
          },
          {
              "forecastTime": "2021-04-02T15:00:00Z",
              "conditionMet": true
          }
      ]
      ```

### General

- Attach to your task a list of things that you think should be optimized/pitfalls in your solution.
- Write any assumptions that you make (if any).
- This is not a production service, but we will be happy to know what is missing to make it one.
- If you think that you are investing too much time on the task, please let us know and write what is missing to complete it.

### Data Source Links

- [File 1](https://storage.googleapis.com/tomorrow-external-access/Data-Team-Candidate-Home-Work-Task/File1.csv)
- [File 2](https://storage.googleapis.com/tomorrow-external-access/Data-Team-Candidate-Home-Work-Task/File2.csv)
- [File 3](https://storage.googleapis.com/tomorrow-external-access/Data-Team-Candidate-Home-Work-Task/File3.csv)

## Questions?

Please contact us, WhatsApp, email, phone call.

**Good luck!**
