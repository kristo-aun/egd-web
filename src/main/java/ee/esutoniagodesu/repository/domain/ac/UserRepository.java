package ee.esutoniagodesu.repository.domain.ac;


import ee.esutoniagodesu.domain.ac.table.ExternalAccountProvider;
import ee.esutoniagodesu.domain.ac.table.User;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(DateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    @Override
    void delete(User t);

    @Query("select u from User u inner join u.externalAccounts ea where ea.externalProvider = ?1 and ea.externalIdentifier = ?2")
    Optional<User> findOneByExternalAccount(ExternalAccountProvider provider, String externalIdentifier);
}
