package pl.devodds.mkozachuk.letsrainnotification.controller;

import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.Place;
import pl.devodds.mkozachuk.letsrainnotification.repository.PlaceRepository;

@Controller
public class PlaceController {
    private PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public void savePlace(Place place){
        placeRepository.save(place);
    }

}
