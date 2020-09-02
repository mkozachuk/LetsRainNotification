package pl.devodds.mkozachuk.letsrainnotification.controller;

import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.User;
import pl.devodds.mkozachuk.letsrainnotification.repository.UserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
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

}
