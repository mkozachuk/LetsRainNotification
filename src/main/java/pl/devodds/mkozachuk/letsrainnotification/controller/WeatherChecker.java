package pl.devodds.mkozachuk.letsrainnotification.controller;

import lombok.extern.slf4j.Slf4j;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.*;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.CurrentWeather;

@Slf4j
@Controller
public class WeatherChecker {
    private OWM owm = new OWM(System.getProperty("weatherapi"));

    public JSONObject getCurrentWeatherData(int cityId) {
        owm.setUnit(OWM.Unit.METRIC);
        JSONObject allCurrentlyWeatherData;
        try {
            allCurrentlyWeatherData = new JSONObject(owm.currentWeatherByCityId(cityId));
        }catch (APIException e){
            log.error("Wheather API Exception : {}", e.getInfo());
            e.printStackTrace();
            allCurrentlyWeatherData = new JSONObject();
        }
        return allCurrentlyWeatherData;
    }

    public JSONObject getHourlyWeatherData(int cityId){
        owm.setUnit(OWM.Unit.METRIC);
        JSONObject allHourlyWeatherData;
        try {
        allHourlyWeatherData = new JSONObject(owm.hourlyWeatherForecastByCityId(cityId));
        }catch (APIException e){
            log.error("Wheather API Exception : {}", e.getInfo());
            e.printStackTrace();
            allHourlyWeatherData = new JSONObject();
        }
        return allHourlyWeatherData;
    }

    public CurrentWeather currentWeatherMaker(JSONObject currentWeatherData){
        CurrentWeather currentWeather = new CurrentWeather();
        JSONObject maindata = new JSONObject(currentWeatherData.getString("mainData"));
        currentWeather.setCurrentTemp(maindata.getDouble("temp"));
        currentWeather.setHumidity(maindata.getInt("humidity"));
        currentWeather.setPressure(maindata.getInt("pressure"));
        maindata = new JSONObject(currentWeatherData.getString("weatherList"));
        currentWeather.setIconLink(maindata.getString("iconLink"));
        currentWeather.setConditionId(maindata.getInt("conditionId"));
        currentWeather.setDescription(maindata.getString("description"));
        maindata = new JSONObject(currentWeatherData.getString("cloudData"));
        currentWeather.setCloudPercent(maindata.getInt("cloud"));
        maindata = null;
        return currentWeather;
    }




    public boolean isItRainyToday (){
        if(true){
            return true;
        }
        else{
            return false;
        }
    }


}
