package honestit.projects.promises.simple.config.starter;

import honestit.projects.promises.simple.users.domain.User;
import honestit.projects.promises.simple.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component @Profile("local")
@RequiredArgsConstructor
public class dataLoader {

    private final UserRepository userRepository;

    @PostConstruct
    public void prepareData() {
        User user = new User();
        user.setUsername("user");
        user.setEmail("user@user.pl");
        user.setPassword("123");
        user.getDetails().setFirstName("Firstname");
        user.getDetails().setLastName("Lastname");

        userRepository.save(user);
    }
}
