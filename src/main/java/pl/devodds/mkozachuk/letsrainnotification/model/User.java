package pl.devodds.mkozachuk.letsrainnotification.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Table(name = "appuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date signdate;

    private String email;
    private UserStatus status;
    private Date notificationtime;

    private String username;
    private String firstname;
    private String lastname;
    private String language;
    private long chatId;

    @Transient
    private String notificationStringTime;

    public enum UserStatus{
        NEW, LETSRAIN, PREMIUM
    }
}
