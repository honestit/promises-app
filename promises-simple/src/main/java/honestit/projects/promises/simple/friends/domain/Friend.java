package honestit.projects.promises.simple.friends.domain;

import honestit.projects.promises.simple.users.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "friends")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Relation relation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "userId")
    private User owner;
    //if exists in database, then have a username
    private String username;

    public boolean isAUser() {
        return username != null;
    }
}