package pl.devodds.mkozachuk.letsrainnotification.repository;

import org.springframework.data.repository.CrudRepository;
import pl.devodds.mkozachuk.letsrainnotification.model.Place;

public interface PlaceRepository extends CrudRepository<Place, Long> {
}
