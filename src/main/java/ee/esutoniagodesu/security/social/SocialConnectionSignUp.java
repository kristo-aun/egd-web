package ee.esutoniagodesu.security.social;

import ee.esutoniagodesu.domain.ac.table.ExternalProvider;
import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.ac.table.UserAccountExternal;
import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import ee.esutoniagodesu.service.MailService;
import ee.esutoniagodesu.service.UserService;
import ee.esutoniagodesu.util.iso.ISO6391;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.ApiException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of ConnectionSignUp that resolves the User login for a social
 * Connection by searching for an UserAccountExternal that matches the Connection.
 */
public class SocialConnectionSignUp implements ConnectionSignUp {

    private static final Logger log = LoggerFactory.getLogger(SocialConnectionSignUp.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public SocialConnectionSignUp(UserRepository userRepository, UserService userService, MailService mailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    private User retreiveSocialAsUser(Connection<?> con) {
        if (con == null) throw new IllegalStateException("No connection to social api");

        // build a new User from the external provider's version of the User
        UserProfile profile = con.fetchUserProfile();
        String firstName = profile.getFirstName();
        String lastName = profile.getLastName();
        String email = profile.getEmail();

        // build the UserAccountExternal from the ConnectionKey
        String externalAccountProviderName = con.getKey().getProviderId();
        ExternalProvider externalProvider = ExternalProvider.caseInsensitiveValueOf(externalAccountProviderName);
        String externalUserId = con.getKey().getProviderUserId();

        // check that we got the information we needed
        if (StringUtils.isBlank(email))
            throw new ApiException(externalAccountProviderName, "provider failed to return email");

        User socialUser = new User(firstName, lastName, email, externalProvider, externalUserId);

        log.debug("Retrieved details from {} for user '{}'", externalAccountProviderName, externalUserId);
        return socialUser;
    }

    /**
     */
    @Transactional
    @Override
    public String execute(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        String providerName = key.getProviderId();
        ExternalProvider externalProvider = ExternalProvider.caseInsensitiveValueOf(providerName);
        String externalId = key.getProviderUserId();

        // try to find an internal user by the social ConnectionKey
        return userRepository.findOneByExternalAccount(externalProvider, externalId)
            .map(user -> {
                String uuid = user.getUuid();
                log.debug("Returning existing internal User.uuid '{}' for external login '{}' from {}",
                    uuid, externalId, externalProvider);
                return uuid;
            }).orElseGet(() -> {
                log.debug("No internal User found for external login '{}' from {}", externalId, externalProvider);
                User socialUser = retreiveSocialAsUser(connection);
                UserAccountExternal socialAccount = socialUser.getAccountExternals().iterator().next();

                return userRepository.findOneByEmail(socialUser.getEmail())
                    .map(user -> {
                        //kasutajal ei tohi olla sama external provideri juures teise id'ga kontot.
                        for (UserAccountExternal p : user.getAccountExternals()) {
                            if (p.getProvider().equals(socialAccount.getProvider())) {
                                throw new IllegalStateException("There is another external login associated with this e-mail");
                            }
                        }

                        log.debug("Add social login to existing user");
                        userService.addExternalToUser(user, socialAccount);
                        mailService.sendWelcomeEmail(user);
                        return user.getUuid();
                    })
                    .orElseGet(() -> {
                        log.debug("Create new user from social information");
                        //täiesti uus kasutaja
                        socialUser.setLangKey(ISO6391.et);
                        log.debug("Send socialUser to creation in userService, shc={}", userService.hashCode());
                        User user = userService.createUserWithExternal(socialUser);
                        mailService.sendWelcomeEmail(user);
                        return user.getUuid();
                    });
            });
    }
}
