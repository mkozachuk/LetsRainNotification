package pl.devodds.mkozachuk.letsrainnotification.notification;

import org.springframework.stereotype.Component;
import pl.devodds.mkozachuk.letsrainnotification.TelegramBot;
import pl.devodds.mkozachuk.letsrainnotification.controller.PlaceController;
import pl.devodds.mkozachuk.letsrainnotification.controller.UserController;
import pl.devodds.mkozachuk.letsrainnotification.controller.WeatherChecker;
import pl.devodds.mkozachuk.letsrainnotification.messages.Messages;
import pl.devodds.mkozachuk.letsrainnotification.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationExecutor {
    private PlaceController placeController;
    private WeatherChecker weatherChecker;
    private TelegramBot bot;
    private Messages messages;
    private UserController userController;

    public NotificationExecutor(UserController userController, PlaceController placeController, WeatherChecker weatherChecker, TelegramBot bot, Messages messages) {
        this.userController = userController;
        this.placeController = placeController;
        this.weatherChecker = weatherChecker;
        this.bot = bot;
        this.messages = messages;
    }

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    public void scheduleNotification() {
        List<User> usersForNotification = userController.getAllForNotification();
        scheduler = Executors.newScheduledThreadPool(usersForNotification.size());
        DateFormat hours = new SimpleDateFormat("HH");
        DateFormat minutes = new SimpleDateFormat("mm");

        for (User user : usersForNotification) {

            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
            ZonedDateTime nextRun = now.withHour(Integer.parseInt(hours.format(user.getNotificationtime()))).withMinute(Integer.parseInt(minutes.format(user.getNotificationtime()))).withSecond(0);
            if (now.compareTo(nextRun) > 0)
                nextRun = nextRun.plusDays(1);

            Duration duration = Duration.between(now, nextRun);
            long initialDelay = duration.getSeconds();
            scheduler.scheduleAtFixedRate(new NotificationTask(placeController, weatherChecker, user, bot, messages),
                    initialDelay,
                    TimeUnit.DAYS.toSeconds(1),
                    TimeUnit.SECONDS);
        }
    }

    public void shutdownScheduler(){
        scheduler.shutdown();
        scheduler.shutdownNow();

    }
}
