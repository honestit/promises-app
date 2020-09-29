package honestit.projects.promises.simple.users.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import honestit.projects.promises.simple.users.domain.User;
import honestit.projects.promises.simple.users.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import honestit.projects.promises.simple.users.UserRegisterRequest;
import honestit.projects.promises.simple.users.UserRegisterResponse;

import javax.swing.text.html.HTMLDocument;

@DisplayName("User Service tests")
class DefaultUserServiceTest {

    private UserRepository userRepository;
    private DefaultUserService service;

    @BeforeEach
    public void prepare() {
        userRepository = Mockito.mock(UserRepository.class);
        service = new DefaultUserService(userRepository);
    }

    @Nested
    @DisplayName("User Service Tests - method registerUser tests")
    class RegisterUserTest {

        private final UserRegisterRequest request = new UserRegisterRequest("User", "User", "User", "Password", "User@User");

        @Test
        @DisplayName("When register user with valid data then user should be saved")
        public void whenRegisterUserWithValidDataThenUserShouldBeSaved() {
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            Mockito.when(userRepository.save(ArgumentMatchers.any()))
                    .thenAnswer(invocation -> {
                        invocation.getArgument(0, User.class).setId(1L);
                        return invocation.getArgument(0, User.class);
                    });

            UserRegisterResponse response = service.register(request);

            Assertions.assertThat(response).isNotNull();
            Assertions.assertThat(response.getUserId()).isNotNull().isEqualTo(1L);
            Assertions.assertThat(response.getUsername()).isNotNull().isEqualTo("User");
        }

        @Test
        @DisplayName("When register user with existing username then should throw exception")
        public void whenRegisterUserWithExistingUsernameThenShouldThrowException() {
            Mockito.when(userRepository.existsByUsername(ArgumentMatchers.any())).thenReturn(true);

            Assertions.assertThatThrownBy(() -> service.register(request))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("When register user with existing email then should throw exception")
        public void whenRegisterUserWithExistingEmailThenShouldThrowException() {
            Mockito.when(userRepository.existsByEmail(ArgumentMatchers.any())).thenReturn(true);

            Assertions.assertThatThrownBy(() -> service.register(request))
                    .isInstanceOf(RuntimeException.class);
        }

    }

}
