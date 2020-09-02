package pl.devodds.mkozachuk.letsrainnotification.controller;

import lombok.extern.slf4j.Slf4j;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.*;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import net.aksingh.owmjapis.model.param.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.Weather;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Controller
public class WeatherChecker {
    @Value("${app.weatherapi}")
    private String apikey = "51853e7a61d644992bb0b3223a0d0313";
    private OWM owm = new OWM(apikey);

    public CurrentWeather getCurrentWeatherData(int cityId) {
        owm.setUnit(OWM.Unit.METRIC);
        try {
            return owm.currentWeatherByCityId(cityId);
        } catch (APIException e) {
            log.error("Wheather API Exception : {}", e.getInfo());
            e.printStackTrace();
            return new CurrentWeather();
        }
    }

    public HourlyWeatherForecast getHourlyWeatherData(int cityId) {
        owm.setUnit(OWM.Unit.METRIC);
        try {
            return owm.hourlyWeatherForecastByCityId(cityId);
        } catch (APIException e) {
            log.error("Wheather API Exception : {}", e.getInfo());
            e.printStackTrace();
            return new HourlyWeatherForecast();
        }
    }

    public Weather currentWeatherMaker(CurrentWeather currentWeatherData) {
        Weather weather = new Weather();

        if (currentWeatherData.hasMainData()) {
            weather.setCurrentTemp(currentWeatherData.getMainData().getTemp());
            weather.setHumidity(currentWeatherData.getMainData().getHumidity().intValue());
            weather.setPressure(currentWeatherData.getMainData().getPressure().intValue());
        } else {
            log.error("NPE There is no MainData");
        }
        if (currentWeatherData.hasWeatherList()) {
            weather.setIconLink(currentWeatherData.getWeatherList().get(0).getIconLink());
            weather.setConditionId(currentWeatherData.getWeatherList().get(0).getConditionId());
            weather.setDescription(currentWeatherData.getWeatherList().get(0).getDescription());
        } else {
            log.error("NPE There is no WeatherList");
        }
        weather.setCloudPercent(currentWeatherData.getCloudData().getCloud().intValue());
        return weather;
    }

    public List<Weather> hourlyWeatherCreator(HourlyWeatherForecast hourlyForecast) {
        List<Weather> hourlyWeather = new ArrayList<>();
        List<WeatherData> dates = new ArrayList<>(hourlyForecast.getDataList());
        for (int i = 0; i < dates.size(); i++) {
            Weather weather = new Weather();
            weather.setDate(dates.get(i).getDateTime());
            if (dates.get(i).hasMainData()) {
                weather.setCurrentTemp(dates.get(i).getMainData().getTemp());
                weather.setHumidity(dates.get(i).getMainData().getHumidity().intValue());
                weather.setPressure(dates.get(i).getMainData().getPressure().intValue());
            } else {
                log.error("NPE There is no MainData");
            }
            if (dates.get(i).hasWeatherList()) {
                weather.setIconLink(dates.get(i).getWeatherList().get(0).getIconLink());
                weather.setConditionId(dates.get(i).getWeatherList().get(0).getConditionId());
                weather.setDescription(dates.get(i).getWeatherList().get(0).getDescription());
            } else {
                log.error("NPE There is no WeatherList");
            }
            weather.setCloudPercent(dates.get(i).getCloudData().getCloud().intValue());
            hourlyWeather.add(weather);
        }

        return hourlyWeather;

    }

    public boolean isItRainyToday(List<Weather> hourlyWeather) {

        boolean rainy = false;
        Date currentDate = new Date();
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int today = localDate.getDayOfMonth();
        LocalDate forecastDate;
        int forecastDay;

        List<Weather> todayHourly = new ArrayList<>();
        for (Weather hourly : hourlyWeather) {
            forecastDate = hourly.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            forecastDay = forecastDate.getDayOfMonth();
            if (forecastDay == today) {
                todayHourly.add(hourly);
            }
        }
        System.out.println(todayHourly);

        for (Weather todayWeather : todayHourly) {
            if (todayWeather.getDescription().contains("rain")) {
                rainy = true;
            }
        }

        return rainy;
    }


}
