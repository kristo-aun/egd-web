package ee.esutoniagodesu.security;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService,
    SocialUserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Inject
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();
        Optional<User> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
        log.debug("userFromDatabase {}", userFromDatabase);

        return userFromDatabase.map(user -> {
            if (!user.isActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
            log.debug("Login successful");
            return new org.springframework.security.core.userdetails.User(user.getUuid(),
                user.getAccountForm().getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    @Override
    @Transactional(readOnly = true)
    public SocialUserDetails loadUserByUserId(final String uuid) throws UsernameNotFoundException, DataAccessException {
        log.debug("Authenticating {} from social login", uuid);

        Optional<User> userFromDatabase = userRepository.findOneByUuid(uuid);

        return userFromDatabase.map(user -> {
            if (!user.isActivated()) {
                throw new UserNotActivatedException("User " + uuid + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
            log.debug("Login successful");
            return new SocialUser(uuid,
                UUID.randomUUID().toString(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + uuid + " was not found in the database"));
    }
}
