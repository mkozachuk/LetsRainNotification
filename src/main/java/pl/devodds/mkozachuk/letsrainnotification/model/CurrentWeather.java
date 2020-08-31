package pl.devodds.mkozachuk.letsrainnotification.model;

import lombok.Data;

@Data
public class CurrentWeather {
    private int humidity;
    private double currentTemp;
    private int pressure;
    private String iconLink;
    private int conditionId;
    private String description;
    private int cloudPercent;
}
