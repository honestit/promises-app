package honestit.projects.promises.simple.users;

public interface UserService {
	
	UserRegisterResponse register(UserRegisterRequest request);
	
	UserDeactivateResponse deactivate(UserDeactivateRequest request);
}
