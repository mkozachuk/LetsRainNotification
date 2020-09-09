package pl.devodds.mkozachuk.letsrainnotification.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.User;
import pl.devodds.mkozachuk.letsrainnotification.notification.NotificationExecutor;
import pl.devodds.mkozachuk.letsrainnotification.repository.UserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class UserController {
    private UserRepository userRepository;
    private NotificationExecutor notificationExecutor;
    public UserController(UserRepository userRepository, @Lazy NotificationExecutor notificationExecutor) {
        this.userRepository = userRepository;
        this.notificationExecutor = notificationExecutor;
    }

    public void save(User user) {
        userRepository.save(user);
        log.info("Data in DB has been change: {}", user);
        notificationExecutor.shutdownScheduler();
        log.info("NotificationExecutor has been shutdown");
        notificationExecutor.scheduleNotification();
        log.info("New Notification Scheduler is UP");
    }

    public boolean isExistInDb(Long chatID) {
        return userRepository.existsByChatId(chatID);
    }
    public List<User> getAllUsers(){
        return userRepository.getAllByStatusIsNotLike(User.UserStatus.LETSRAIN);
    }
    public User getUserInDBChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId);
    }
    public User getUserInDB(String username) {
        return userRepository.findUserByUsername(username);
    }
    public List<User> getAdmins(){
        return userRepository.getUsersByStatus(User.UserStatus.LETSRAIN);
    }

    public List<User> getTodaysUser(){
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, -1); // number represents number of days
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date yesterday = cal.getTime();
        return userRepository.getUsersBySigndateAfter(yesterday);
    }

    public List<User> getLastSevenDaysUsers(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -6); // number represents number of days
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date sevenDaysAgo = cal.getTime();
        return userRepository.getUsersBySigndateAfter(sevenDaysAgo);
    }

    public List<User> getLastThirtyDaysUsers(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -29); // number represents number of days
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date thirtyDaysAgo = cal.getTime();
        return userRepository.getUsersBySigndateAfter(thirtyDaysAgo);
    }

    public List<User> getAllForNotification(){
        return userRepository.findAllByNotificationtimeIsNotNullOrderByNotificationtime();
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
