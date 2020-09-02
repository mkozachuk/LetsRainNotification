package pl.devodds.mkozachuk.letsrainnotification.model;

import lombok.Data;

import java.util.Date;

@Data
public class Weather {
    private int humidity;
    private double currentTemp;
    private int pressure;
    private String iconLink;
    private int conditionId;
    private String description;
    private int cloudPercent;
    private Date date;
}
