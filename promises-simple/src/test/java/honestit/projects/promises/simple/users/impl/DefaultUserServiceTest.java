package honestit.projects.promises.simple.users.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import honestit.projects.promises.simple.users.UserRegisterRequest;
import honestit.projects.promises.simple.users.UserRegisterResponse;


@SpringBootTest @ActiveProfiles("local")
class DefaultUserServiceTest {
	
	@Autowired
	private DefaultUserService service;

	@Test
	void givenValidUserRegisterRequest_whenRegister_thenShouldBeOk() {
		// given
		UserRegisterRequest request = new UserRegisterRequest("test", "test", "test", "test", "test");
		
		// when
		UserRegisterResponse response = service.register(request);
		
		// then
		assertNotNull(response);
		assertNotNull(response.getUserId());
		assertEquals("test", response.getUsername());
	}
	
	@Test 
	public void givenUserRegisterRequestWithExistingUsername_whenRegister_thenThrowException() {
		UserRegisterRequest request = new UserRegisterRequest("username1", "pass", "test", "test", "test2");
		
		service.register(request);
		org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> service.register(request));
	}

}
