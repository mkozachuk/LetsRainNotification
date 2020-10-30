package pl.devodds.mkozachuk.letsrainnotification.controller;

import org.springframework.stereotype.Controller;
import pl.devodds.mkozachuk.letsrainnotification.model.Quote;
import pl.devodds.mkozachuk.letsrainnotification.repository.QuoteRepository;

import java.util.List;

@Controller
public class QuoteController {
    private QuoteRepository quoteRepository;
    public QuoteController(QuoteRepository quoteRepository){
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> getAllQuotes(){
        return (List<Quote>) quoteRepository.findAll();
    }
}
