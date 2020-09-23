package honestit.projects.promises.simple.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserRegisterResponse {
	
	private Long userId;
	private String username;

}
