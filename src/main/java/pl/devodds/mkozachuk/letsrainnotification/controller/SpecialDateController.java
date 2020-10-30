package pl.devodds.mkozachuk.letsrainnotification.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.SpecialDate;
import pl.devodds.mkozachuk.letsrainnotification.model.User;
import pl.devodds.mkozachuk.letsrainnotification.repository.SpecialDateRepository;

import java.util.List;

@Controller
public class SpecialDateController {
    private SpecialDateRepository specialDateRepository;

    public SpecialDateController(SpecialDateRepository specialDateRepository){
        this.specialDateRepository = specialDateRepository;
    }

    public void save(SpecialDate specialDate){
        specialDateRepository.save(specialDate);
    }

    public List<SpecialDate> finAllByUser(User user){
        return specialDateRepository.findAllByUser(user);
    }

    public void deleteSpecialDate(SpecialDate specialDate){
        specialDateRepository.delete(specialDate);
    }


}
