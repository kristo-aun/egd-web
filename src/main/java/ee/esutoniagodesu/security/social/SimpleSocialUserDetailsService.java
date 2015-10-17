package ee.esutoniagodesu.security.social;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import ee.esutoniagodesu.security.UserNotActivatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SimpleSocialUserDetailsService implements SocialUserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SimpleSocialUserDetailsService.class);

    private final UserRepository userRepository;

    public SimpleSocialUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toList());
            log.debug("Login successful");
            return new SocialUser(uuid,
                UUID.randomUUID().toString(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + uuid + " was not found in the database"));
    }
}
