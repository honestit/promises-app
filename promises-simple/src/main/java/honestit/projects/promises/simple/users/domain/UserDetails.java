package honestit.projects.promises.simple.users.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter @Setter @ToString @EqualsAndHashCode
public class UserDetails {

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
}
