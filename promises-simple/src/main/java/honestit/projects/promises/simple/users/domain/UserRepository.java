package honestit.projects.promises.simple.users.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

    User getByUsername(String username);
}
