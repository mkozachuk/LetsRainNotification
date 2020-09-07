package pl.devodds.mkozachuk.letsrainnotification.notification;

import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pl.devodds.mkozachuk.letsrainnotification.TelegramBot;
import pl.devodds.mkozachuk.letsrainnotification.controller.PlaceController;
import pl.devodds.mkozachuk.letsrainnotification.controller.UserController;
import pl.devodds.mkozachuk.letsrainnotification.controller.WeatherChecker;
import pl.devodds.mkozachuk.letsrainnotification.messages.Messages;
import pl.devodds.mkozachuk.letsrainnotification.model.Place;
import pl.devodds.mkozachuk.letsrainnotification.model.User;
import pl.devodds.mkozachuk.letsrainnotification.model.Weather;

import java.util.List;

public class NotificationTask implements Runnable {

    private PlaceController placeController;
    private WeatherChecker weatherChecker;
    private User user;
    private TelegramBot bot;
    private Messages messages;
    private InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();

    public NotificationTask(PlaceController placeController, WeatherChecker weatherChecker, User user, TelegramBot bot, Messages messages) {
        this.placeController = placeController;
        this.weatherChecker = weatherChecker;
        this.user = user;
        this.bot = bot;
        this.messages = messages;
    }

    @Override
    public void run() {
        List<Place> allPlacesByUser = placeController.findAllPlacesByUser(user);
        for (Place place : allPlacesByUser) {
            HourlyWeatherForecast hourlyWeatherForecast = weatherChecker.getHourlyWeatherData(place.getLat(), place.getLon());
            List<Weather> weathers = weatherChecker.hourlyWeatherCreator(hourlyWeatherForecast);
            if (weatherChecker.isItRainyToday(weathers)) {
                bot.sendAnyMsgToCurrentUser(messages.itsRainyToday(place), user.getChatId(), inlineMarkup);
            }
        }
    }
}
