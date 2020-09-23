package honestit.projects.promises.simple.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor
public class UserRegisterRequest {
	
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
}
