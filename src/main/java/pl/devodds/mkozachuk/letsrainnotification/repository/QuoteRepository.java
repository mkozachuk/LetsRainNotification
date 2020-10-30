package pl.devodds.mkozachuk.letsrainnotification.repository;

import org.springframework.data.repository.CrudRepository;
import pl.devodds.mkozachuk.letsrainnotification.model.Quote;

public interface QuoteRepository extends CrudRepository<Quote,Long> {
}
