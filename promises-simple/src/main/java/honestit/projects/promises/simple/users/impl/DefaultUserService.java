package honestit.projects.promises.simple.users.impl;

import org.springframework.stereotype.Service;

import honestit.projects.promises.simple.users.UserDeactivateRequest;
import honestit.projects.promises.simple.users.UserDeactivateResponse;
import honestit.projects.promises.simple.users.UserRegisterRequest;
import honestit.projects.promises.simple.users.UserRegisterResponse;
import honestit.projects.promises.simple.users.UserService;
import honestit.projects.promises.simple.users.domain.User;
import honestit.projects.promises.simple.users.domain.UserDetails;
import honestit.projects.promises.simple.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j @RequiredArgsConstructor
public class DefaultUserService implements UserService {

	private final UserRepository userRepository;
	
	@Override
	public UserRegisterResponse register(UserRegisterRequest request) {
		log.debug("Data to register user: {}", request);
		
		if(userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalStateException("User with this username already exist");
			
		}
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		
		user.getDetails().setFirstName(request.getFirstName());
		user.getDetails().setLastName(request.getLastName());
				
		log.debug("User to save: {}", user);
		userRepository.save(user);
		log.debug("Saved user: {}", user);
		
		return new UserRegisterResponse(user.getId(), user.getUsername());
	}

	@Override
	public UserDeactivateResponse deactivate(UserDeactivateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
