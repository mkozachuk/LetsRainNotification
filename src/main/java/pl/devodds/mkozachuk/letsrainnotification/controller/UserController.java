package pl.devodds.mkozachuk.letsrainnotification.controller;

import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.User;
import pl.devodds.mkozachuk.letsrainnotification.repository.UserRepository;

@Controller
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void save(User user){
        userRepository.save(user);
    }
}
