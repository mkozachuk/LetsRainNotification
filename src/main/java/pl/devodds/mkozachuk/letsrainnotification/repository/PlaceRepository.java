package pl.devodds.mkozachuk.letsrainnotification.repository;

import org.springframework.data.repository.CrudRepository;
import pl.devodds.mkozachuk.letsrainnotification.model.Place;
import pl.devodds.mkozachuk.letsrainnotification.model.User;

import java.util.List;

public interface PlaceRepository extends CrudRepository<Place, Long> {
    List<Place> findAllByUser(User user);
}
