package pl.devodds.mkozachuk.letsrainnotification.repository;

import org.springframework.data.repository.CrudRepository;
import pl.devodds.mkozachuk.letsrainnotification.model.User;

import java.util.Date;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsByChatId(Long chatID);
    List<User> getUsersBySigndateAfter(Date after);
    List<User> getUsersByStatus(User.UserStatus status);
    List<User> getAllByStatusIsNotLike(User.UserStatus status);
    User findUserByChatId(Long chatId);
    User findUserByUsername(String username);
}
