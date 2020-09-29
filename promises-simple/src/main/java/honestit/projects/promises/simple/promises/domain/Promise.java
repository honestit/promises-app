package honestit.projects.promises.simple.promises.domain;

import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.users.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "promises")
@Getter @Setter @ToString @EqualsAndHashCode(of = "id")
public class Promise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "whom_id")
    private Friend whom;
    @Column(name = "till_day")
    private LocalDate tillDay;
    @Column(name = "till_time")
    private LocalTime tillTime;
    @ManyToOne
    @JoinColumn(name = "who_id")
    private User who;

    private Boolean kept;
    @Column(name = "kept_date")
    private LocalDateTime keptDate;
}
