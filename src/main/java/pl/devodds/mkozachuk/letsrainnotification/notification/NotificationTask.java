package pl.devodds.mkozachuk.letsrainnotification.notification;

import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import pl.devodds.mkozachuk.letsrainnotification.TelegramBot;
import pl.devodds.mkozachuk.letsrainnotification.controller.PlaceController;
import pl.devodds.mkozachuk.letsrainnotification.controller.WeatherChecker;
import pl.devodds.mkozachuk.letsrainnotification.messages.Messages;
import pl.devodds.mkozachuk.letsrainnotification.model.Place;
import pl.devodds.mkozachuk.letsrainnotification.model.User;
import pl.devodds.mkozachuk.letsrainnotification.model.Weather;
import pl.devodds.mkozachuk.letsrainnotification.service.EmailServiceImpl;

import java.util.List;

public class NotificationTask implements Runnable {

    private PlaceController placeController;
    private WeatherChecker weatherChecker;
    private User user;
    private TelegramBot bot;
    private Messages messages;
    private InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();
    private EmailServiceImpl emailService;

    public NotificationTask(PlaceController placeController, WeatherChecker weatherChecker, User user, TelegramBot bot, Messages messages, EmailServiceImpl emailService) {
        this.placeController = placeController;
        this.weatherChecker = weatherChecker;
        this.user = user;
        this.bot = bot;
        this.messages = messages;
        this.emailService = emailService;
    }

    @Override
    public void run() {
        List<Place> allPlacesByUser = placeController.findAllPlacesByUser(user);
        for (Place place : allPlacesByUser) {
            HourlyWeatherForecast hourlyWeatherForecast = weatherChecker.getHourlyWeatherData(place.getLat(), place.getLon());
            List<Weather> weathers = weatherChecker.hourlyWeatherCreator(hourlyWeatherForecast);
            if (weatherChecker.isItRainyToday(weathers)) {
                if (user.getChatId() != 0) {
                    bot.sendUmbrellaSticker(String.valueOf(user.getChatId()));
                    bot.sendAnyMsgToCurrentUser(messages.itsRainyToday(place), user.getChatId(), inlineMarkup, new ReplyKeyboardMarkup());
                }
                if (!user.getEmail().isEmpty()) {
                    emailService.sendMailNotification(user.getEmail(), place);
                }
            }
        }
    }
}
