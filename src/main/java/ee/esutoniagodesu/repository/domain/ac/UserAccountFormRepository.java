package ee.esutoniagodesu.repository.domain.ac;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.ac.table.UserAccountForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface UserAccountFormRepository extends JpaRepository<UserAccountForm, Integer> {
    Optional<User> findOneByLogin(String login);
}
