package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.*;
import ee.esutoniagodesu.repository.domain.ac.PersistentTokenRepository;
import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.service.MailService;
import ee.esutoniagodesu.service.UserService;
import ee.esutoniagodesu.util.lang.ISO6391;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.ApiException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private MailService mailService;

    @Inject
    private ConnectionFactoryLocator connectionFactoryLocator;

    private RelaxedPropertyResolver appSecurityPropertyResolver;

    @Override
    public void setEnvironment(Environment env) {
        this.appSecurityPropertyResolver = new RelaxedPropertyResolver(env, "app.security.");
    }

    private boolean isActivationRequired() {
        return Boolean.valueOf(appSecurityPropertyResolver.getProperty("activationRequired"));
    }

    @RequestMapping(value = "/register",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody User newUser, HttpServletRequest request) {
        log.debug("REST request to register {}", newUser);

        return userRepository.findOneByLogin(newUser.getAccountForm().getLogin())
            .map(user -> new ResponseEntity<>("login already in use", HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(newUser.getEmail())
                    .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
                    .orElseGet(() -> {
                        User user = userService.createUserWithAccountForm(newUser);
                        if (isActivationRequired()) {
                            mailService.sendActivationEmail(user, getBaseUrl(request));
                        } else {
                            mailService.sendWelcomeEmail(user, getBaseUrl(request));
                        }

                        return new ResponseEntity<>(HttpStatus.CREATED);
                    })
            );
    }

    /**
     * GET  /activate -> activate the registered user.
     */
    @RequestMapping(value = "/activate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return Optional.ofNullable(userService.activateRegistration(key))
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate -> check if the user is authenticated, and return its login.
     */
    @RequestMapping(value = "/authenticate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account -> get the current user.
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account -> update the current user information.
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveAccount(@RequestBody User saveUser) {
        log.debug("REST request to saveAccount {}", saveUser);

        return userRepository.findOneByEmailNotThisUuid(saveUser.getEmail(), SecurityUtils.getUserUuid())
            .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
            .orElseGet(() -> {
                userService.updateUserInformation(saveUser.getFirstName(),
                    saveUser.getLastName(),
                    saveUser.getEmail(),
                    saveUser.getLangKey());
                return new ResponseEntity<>(HttpStatus.OK);
            });
    }

    /**
     * POST  /change_password -> changes the current user's password
     */
    @RequestMapping(value = "/account/change_password",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET  /account/sessions -> get the current open sessions.
     */
    @RequestMapping(value = "/account/sessions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersistentToken>> getCurrentSessions() {
        return userRepository.findOneByUuid(SecurityUtils.getUserUuid())
            .map(user -> new ResponseEntity<>(
                persistentTokenRepository.findByUser(user),
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * DELETE  /account/sessions?series={series} -> invalidate an existing session.
     * <p/>
     * - You can only delete your own sessions, not any other user's session
     * - If you delete one of your existing sessions, and that you are currently logged in on that session, you will
     * still be able to use that session, until you quit your browser: it does not work in real time (there is
     * no API for that), it only removes the "remember me" cookie
     * - This is also true if you invalidate your current session: you will still be able to use it until you close
     * your browser or that the session times out. But automatic login (the "remember me" cookie) will not work
     * anymore.
     * There is an API to invalidate the current session, but there is no API to check which session uses which
     * cookie.
     */
    @RequestMapping(value = "/account/sessions/{series}",
        method = RequestMethod.DELETE)
    public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        userRepository.findOneByUuid(SecurityUtils.getUserUuid()).ifPresent(u -> {
            persistentTokenRepository.findByUser(u).stream()
                .filter(persistentToken -> StringUtils.equals(persistentToken.getSeries(), decodedSeries))
                .findAny().ifPresent(t -> persistentTokenRepository.delete(decodedSeries));
        });
    }

    @RequestMapping(value = "/account/reset_password/init",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail, HttpServletRequest request) {
        return userService.requestPasswordReset(mail)
            .map(user -> {
                mailService.sendPasswordResetMail(user, getBaseUrl(request));
                return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
            }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
    }

    @RequestMapping(value = "/account/reset_password/finish",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> finishPasswordReset(@RequestParam(value = "key") String key,
                                                      @RequestParam(value = "newPassword") String newPassword) {
        if (!checkPasswordLength(newPassword)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(newPassword, key)
            .map(user -> new ResponseEntity<String>(HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
            password.length() >= UserAccountForm.PASSWORD_MIN_LENGTH &&
            password.length() <= UserAccountForm.PASSWORD_MAX_LENGTH);
    }


    private final static String EXTERNAL_AUTH_AS_USERDTO_KEY = "AccountResource.signInAsUser";

    /**
     * Build a new Connection to a social provider using the information from a previous
     * sign in.
     *
     * @param attempt a possibly null ProviderSignInAttempt
     * @return null a valid Connection or null if attempt was null or a Connection could not be established
     */
    private Connection<?> buildConnection(ProviderSignInAttempt attempt) {
        if (attempt == null) return null;

        Connection<?> connection = attempt.getConnection(connectionFactoryLocator);

        if (!connection.test()) {
            log.warn("Social connection to {} for user '{}' failed", connection.getKey().getProviderId(),
                connection.getKey().getProviderUserId());
            return null;
        }
        return connection;
    }

    /**
     * Retrieve a previous external social authentication attempt as a User.
     *
     * @param request a non-null request
     * @return a User with the firstName, lastName, email, and externalAccount details set or null if
     * there was no previous social authentication attempt or the details of the social authentication
     * could not be retrieved.
     * @throws org.springframework.social.ApiException when the social API does not return the required attributes (name, email)
     * @see org.springframework.social.connect.web.ProviderSignInAttempt
     * @see org.springframework.social.security.SocialAuthenticationFilter#addSignInAttempt(javax.servlet.http.HttpSession, org.springframework.social.connect.Connection) SocialAuthenticationFilter#addSignInAttempt
     */
    private Optional<User> retreiveSocialAsUser(HttpServletRequest request) {
        User socialUser = (User) WebUtils.getSessionAttribute(request, EXTERNAL_AUTH_AS_USERDTO_KEY);

        log.debug("retrieve social user from request {}", socialUser);

        if (socialUser == null) {
            // check if the user was successfully authenticated against an external service
            // but failed to authenticate against this application.
            ProviderSignInAttempt attempt = (ProviderSignInAttempt) WebUtils.getSessionAttribute(request,
                ProviderSignInAttempt.SESSION_ATTRIBUTE);
            Connection<?> con = buildConnection(attempt);

            if (con == null)
                throw new ApiException(attempt.toString(), "no connection to social api");

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
            if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName) || StringUtils.isBlank(email))
                throw new ApiException(externalAccountProviderName, "provider failed to return required attributes");

            socialUser = new User(firstName, lastName, email, externalProvider, externalUserId);

            // save the new User for later and clean up the HttpSession
            request.getSession().removeAttribute(ProviderSignInAttempt.SESSION_ATTRIBUTE);
            request.getSession().setAttribute(EXTERNAL_AUTH_AS_USERDTO_KEY, socialUser);

            log.debug("Retrieved details from {} for user '{}'", externalAccountProviderName, externalUserId);
        }
        return Optional.of(socialUser);
    }

    private void finishExternal(User user, HttpServletRequest request) {
        if (isActivationRequired()) {
            mailService.sendActivationEmail(user, getBaseUrl(request));
        } else {
            mailService.sendWelcomeEmail(user, getBaseUrl(request));
        }

        // cleanup the social stuff that we've been keeping in the session
        request.getSession().removeAttribute(EXTERNAL_AUTH_AS_USERDTO_KEY);
    }

    /**
     * Kolm varianti
     * 1) Täiesti uus kasutaja, emailiga kasutajat ei ole, social id on registreerimata. Teha User ja External.
     * 2) Social providerist saadud emailiga kasutaja on olemas. Lisa External ja seo User-iga.
     * 3) Social id on juba registreeritud. Ei lase uuesti registreeruda, isegi kui social email ja konto email erinevad.
     */
    @RequestMapping(value = "/register/external",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> registerExternal(HttpServletRequest request) {
        //leia requesti järgi social info
        return retreiveSocialAsUser(request)
            .map(socialUser -> {
                UserAccountExternal socialAccount = socialUser.getAccountExternals().iterator().next();

                return userRepository.findOneByExternalAccount(socialAccount.getProvider(), socialAccount.getIdentifier())
                    //kui Social id on juba registreeritud, siis ei lase uuesti registreerida
                    .map(userWithSameExternal -> new ResponseEntity<String>("The external login is already linked to another User",
                        HttpStatus.BAD_REQUEST))
                    .orElseGet(() -> userRepository.findOneByEmail(socialUser.getEmail())
                            .map(user -> {
                                //kasutajal ei tohi olla sama external provideri juures teise id'ga kontot.
                                for (UserAccountExternal p : user.getAccountExternals()) {
                                    if (p.getProvider().equals(socialAccount.getProvider())) {
                                        return new ResponseEntity<String>(
                                            "There is another external login associated with this e-mail",
                                            HttpStatus.BAD_REQUEST);
                                    }
                                }

                                userService.addExternalToUser(user, socialAccount);
                                finishExternal(user, request);

                                return new ResponseEntity<String>(HttpStatus.CREATED);
                            })
                            .orElseGet(() -> {
                                //täiesti uus kasutaja
                                socialUser.setLangKey(ISO6391.et);
                                User user = userService.createUserWithExternal(socialUser);
                                finishExternal(user, request);
                                return new ResponseEntity<String>(HttpStatus.CREATED);
                            })
                    );
            })
            .orElse(new ResponseEntity<String>("could not retreive social account data", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET details of an ongoing registration
     *
     * @return 200 OK or 404 if there is no ongoing registration
     */
    @RequestMapping(value = "/register/external",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRegisterAccount(HttpServletRequest request) {
        return retreiveSocialAsUser(request)
            .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + // "https"
            "://" +                                // "://"
            request.getServerName() +              // "myhost"
            ":" +                                  // ":"
            request.getServerPort();
    }


    @RequestMapping(value = "/account",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete() {
        userService.deleteAccount();
    }
}
