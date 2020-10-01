package honestit.projects.promises.simple.promises.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PromiseRepository extends JpaRepository<Promise, Long> {

    @Query("SELECT p " +
            "FROM Promise p " +
            "WHERE " +
                "p.who.username = ?1 " +
                "AND p.kept IS NULL " +
                "AND (p.tillDay > ?2 " +
                    "OR (p.tillDay = ?2 " +
                    "AND p.tillTime > ?3))")
    List<Promise> findAllNullKeptPromisesForUserWithDeadlineBefore(String username, LocalDate tillDate, LocalTime tillTime);
}
