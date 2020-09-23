package honestit.projects.promises.simple.web.controllers.registration;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegistrationForm {
	
	@NotBlank @Size(min = 3, max = 12)
	private String username;
	@NotBlank
	private String password;
	private String rePassword;
	@NotBlank @Email
	private String email;

}
