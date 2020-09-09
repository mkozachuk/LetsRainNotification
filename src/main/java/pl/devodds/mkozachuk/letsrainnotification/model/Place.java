package pl.devodds.mkozachuk.letsrainnotification.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date addat;

    private String cityname;    //Warsaw
    private long cityid;    //756135

    private double lon;    //21.01178
    private double lat;    //52.229771

    @ManyToOne
    private User user;

    @Transient
    private String email;

}
