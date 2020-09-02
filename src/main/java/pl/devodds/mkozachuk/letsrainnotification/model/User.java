package pl.devodds.mkozachuk.letsrainnotification.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String status;

    private String username;
    private String firstname;
    private String lastname;
    private String language;
    private long chatId;
}
