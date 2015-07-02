package ee.esutoniagodesu.security.social;

import ee.esutoniagodesu.domain.ac.table.ExternalAccountProvider;
import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * An implementation of ConnectionSignUp that resolves the User login for a social
 * Connection by searching for an ExternalAccount that matches the Connection.
 */
@Component("socialConnectionSignUp")
public class SocialConnectionSignUp implements ConnectionSignUp {

    private static final Logger log = LoggerFactory.getLogger(SocialConnectionSignUp.class);

    @Inject
    private UserRepository userRepository;

    /**
     * Map a Connection to an existing User by searching for an ExternalAccount that matches
     * the Connection's {@link ConnectionKey ConnectionKey}.  For example,
     * given a ConnectionKey with a providerId of "google" and a providerUserId of "12345691011",
     * search for an ExternalAccount that matches and return the {@link ee.esutoniagodesu.domain.ac.table.User#getLogin() login}
     * associated with the account.
     *
     * @param connection a non-null Connection
     * @return a User login if the Connection matched an existing User, null otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public String execute(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        String providerName = key.getProviderId();
        ExternalAccountProvider externalProvider = ExternalAccountProvider.caseInsensitiveValueOf(providerName);
        String externalId = key.getProviderUserId();

        // try to find an internal user based on the social ConnectionKey.  for example, something like "google" "12345691011".
        User user = userRepository.findOneByExternalAccount(externalProvider, externalId);
        if (user != null) {
            String internalLogin = user.getLogin();
            log.debug("Returning existing internal User '{}' for external login '{}' from {}", internalLogin, externalId, externalProvider);
            return internalLogin;
        } else {
            log.debug("No internal User for external login '{}' from {}", externalId, externalProvider);
            return null;
        }
    }
}
