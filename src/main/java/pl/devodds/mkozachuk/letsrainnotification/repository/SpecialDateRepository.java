package pl.devodds.mkozachuk.letsrainnotification.repository;

import org.springframework.data.repository.CrudRepository;
import pl.devodds.mkozachuk.letsrainnotification.model.SpecialDate;
import pl.devodds.mkozachuk.letsrainnotification.model.User;

import java.util.List;

public interface SpecialDateRepository extends CrudRepository<SpecialDate, Long> {

    List<SpecialDate> findAllByUser(User user);
}
