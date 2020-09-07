package pl.devodds.mkozachuk.letsrainnotification.controller;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.Place;
import pl.devodds.mkozachuk.letsrainnotification.model.User;
import pl.devodds.mkozachuk.letsrainnotification.repository.PlaceRepository;

import java.util.Date;
import java.util.List;

@Controller
public class PlaceController {
    private PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public void savePlace(Place place){
        placeRepository.save(place);
    }

    public List<Place> findAllPlacesByUser(User user){
        return placeRepository.findAllByUser(user);
    }

    public Place placeCreator(OWM owm, User user, double lat, double lon) throws APIException {
        Place newPlace = new Place();
        newPlace.setUser(user);
        newPlace.setAddat(new Date());
        newPlace.setLat(lat);
        newPlace.setLon(lon);
        newPlace.setCityid(owm.currentWeatherByCoords(lat,lon).getCityId());
        newPlace.setCityname(owm.currentWeatherByCoords(lat,lon).getCityName());
        return newPlace;
    }

}
