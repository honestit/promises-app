package honestit.projects.promises.simple.friends.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByNameAndOwnerUsername(String name, String username);

    Friend getByNameAndOwnerUsername(String name, String username);
}
