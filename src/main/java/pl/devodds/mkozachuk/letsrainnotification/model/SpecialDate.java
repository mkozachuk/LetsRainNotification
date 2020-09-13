package pl.devodds.mkozachuk.letsrainnotification.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class SpecialDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private boolean repeat;
    private String description;

    @ManyToOne
    private User user;
}
