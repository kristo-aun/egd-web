package ee.esutoniagodesu.service;

import ee.esutoniagodesu.domain.ac.table.Authority;
import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.ac.table.UserAccountExternal;
import ee.esutoniagodesu.repository.domain.ac.PersistentTokenRepository;
import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.util.JCRandom;
import ee.esutoniagodesu.util.RandomUtil;
import ee.esutoniagodesu.util.iso.ISO6391;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                log.debug("Activated user: {}", user);
                return user;
            });
        return Optional.empty();
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userRepository.findOneByResetKey(key)
            .filter(user -> {
                DateTime oneDayAgo = DateTime.now().minusHours(24);
                return user.getAccountForm().getResetDate().isAfter(oneDayAgo.toInstant().getMillis());
            })
            .map(user -> {
                user.getAccountForm().setPassword(passwordEncoder.encode(newPassword));
                user.getAccountForm().setResetKey(null);
                user.getAccountForm().setResetDate(null);
                userRepository.save(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::isActivated)
            .map(user -> {
                user.getAccountForm().setResetKey(RandomUtil.generateResetKey());
                user.getAccountForm().setResetDate(DateTime.now());
                userRepository.save(user);
                return user;
            });
    }

    private User newUser(User newUser) {

        newUser.setUuid(JCRandom.random13B());

        Set<Authority> roles = new HashSet<>();

        roles.add(Authority.ROLE_USER);
        newUser.setRoles(roles);
        return newUser;
    }

    public User addExternalToUser(User user, UserAccountExternal external) {
        external.setUser(user);
        user.getAccountExternals().add(external);
        userRepository.save(user);

        log.debug("Created External Information {} for Social User: {}", external, user);
        return user;
    }

    public User createUserWithExternal(User newUser) {
        newUser = newUser(newUser);
        newUser.setActivated(true);

        UserAccountExternal external = newUser.getAccountExternals().iterator().next();

        external.setUser(newUser);
        userRepository.save(newUser);

        log.debug("Created External Information {} for Social User: {}", external, newUser);
        return newUser;
    }

    public User createUserWithAccountForm(User newUser) {

        newUser = newUser(newUser);

        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());

        // new user gets initially a generated password
        String encryptedPassword = passwordEncoder.encode(newUser.getAccountForm().getPassword());
        newUser.getAccountForm().setPassword(encryptedPassword);
        newUser.getAccountForm().setUser(newUser);

        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public void updateUserInformation(String firstName, String lastName, String email, ISO6391 langKey) {
        securityUser().ifPresent(u -> {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            u.setLangKey(langKey);
            userRepository.save(u);
            log.debug("Changed Information for User: {}", u);
        });
    }

    public void changePassword(String password) {
        securityUser().ifPresent(u -> {
            String encryptedPassword = passwordEncoder.encode(password);
            u.getAccountForm().setPassword(encryptedPassword);
            userRepository.save(u);
            log.debug("Changed password for User: {}", u);
        });
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        User currentUser = securityUser().get();
        currentUser.getRoles().size(); // eagerly load the association
        return currentUser;
    }

    private Optional<User> securityUser() {
        return userRepository.findOneByUuid(SecurityUtils.getUserUuid());
    }

    public void deleteAccount() {
        if (!SecurityUtils.isAuthenticated()) {
            throw new IllegalStateException("not authenticated");
        }
        if (SecurityUtils.isUserInRole(AuthoritiesConstants.ADMIN)) {
            throw new IllegalStateException("delete admin account not allowed");
        }

        securityUser().ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted account for User: {}", user);
        });
    }

    /**
     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
     * 30 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     * </p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = new LocalDate();
        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).stream().forEach(token -> {
            log.debug("Deleting token {}", token.getSeries());
            User user = token.getUser();
            user.getPersistentTokens().remove(token);
            persistentTokenRepository.delete(token);
        });
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        DateTime now = new DateTime();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.toString());
            userRepository.delete(user);
        }
    }
}
