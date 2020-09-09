package pl.devodds.mkozachuk.letsrainnotification.controller;

import lombok.extern.slf4j.Slf4j;
import net.aksingh.owmjapis.api.APIException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.devodds.mkozachuk.letsrainnotification.model.Place;
import pl.devodds.mkozachuk.letsrainnotification.model.User;
import pl.devodds.mkozachuk.letsrainnotification.model.Weather;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private PlaceController placeController;
    private UserController userController;
    private WeatherChecker weatherChecker;

    public NotificationController(PlaceController placeController, UserController userController, WeatherChecker weatherChecker){
        this.placeController = placeController;
        this.userController = userController;
        this.weatherChecker = weatherChecker;
    }

    @ModelAttribute(name = "newUser")
    public User user() {
        return new User();
    }

    @GetMapping("/signup")
    public String newUser(Model model){
        model.addAttribute("newUser", user());
        return "signup";
    }

    @PostMapping("/signup")
    public String processNewUser(@ModelAttribute("newUser") User user, Errors errors){
        System.out.println(user);
        user.setSigndate(new Date());
        user.setStatus(User.UserStatus.NEW);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            user.setNotificationtime(dateFormat.parse(user.getNotificationStringTime()));
        }catch (ParseException e){
            log.error("Parse exception");
            e.printStackTrace();
        }
        System.out.println(user);
        userController.save(user);

        return "/notifications/newplace";
    }

    @ModelAttribute(name = "newUser")
    public Place place() {
        return new Place();
    }

    @GetMapping("/newplace")
    public String newPlace(Model model){
        model.addAttribute("newPlace", place());
        return "newplace";
    }

    @PostMapping("/newplace")
    public String processNewPlace(@ModelAttribute("newPlace") Place place, Errors errors){
        try {
            System.out.println(place);
            place.setAddat(new Date());
            placeController.savePlace(placeController.placeCreator(weatherChecker.getOwm(), userController.findByEmail(place.getEmail()), place.getLat(), place.getLon()));
            System.out.println(place);
        }catch (APIException e){
            log.error("API Exception");
            e.printStackTrace();
        }
        return "redirect:/";
    }

}
