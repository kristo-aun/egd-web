package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.ac.table.UserAccountForm;
import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.service.MailService;
import ee.esutoniagodesu.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    public static final String LOGIN_IN_USE = "login already in use";
    public static final String EMAIL_IN_USE = "e-mail address already in use";
    public static final String INCORRECT_PASSWORD = "Incorrect password";
    public static final String EMAIL_NOT_REGISTERED = "e-mail address not registered";
    public static final String EMAIL_WAS_SENT = "e-mail was sent";

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    @RequestMapping(value = "/register",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody User newUser) {
        log.debug("REST request to register {}", newUser);
        return userRepository.findOneByLogin(newUser.getAccountForm().getLogin())
            .map(user -> new ResponseEntity<>(LOGIN_IN_USE, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(newUser.getEmail())
                    .map(user -> new ResponseEntity<>(EMAIL_IN_USE, HttpStatus.BAD_REQUEST))
                    .orElseGet(() -> {
                        User user = userService.createUserWithAccountForm(newUser);
                        mailService.sendActivationEmail(user);
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
    public ResponseEntity<String> activateAccount(@RequestParam String key) {
        return Optional.ofNullable(userService.activateRegistration(key))
            .map(user -> {
                mailService.sendWelcomeEmail(user.get());
                return new ResponseEntity<String>(HttpStatus.OK);
            })
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
            .map(user -> new ResponseEntity<>(EMAIL_IN_USE, HttpStatus.BAD_REQUEST))
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
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body) {
        String password = body.get("password");
        System.out.println(password);
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>(INCORRECT_PASSWORD, HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(body.get("password"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/reset_password/init",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> body) {
        String mail = body.get("mail");
        return userService.requestPasswordReset(mail)
            .map(user -> {
                mailService.sendPasswordResetMail(user);
                return new ResponseEntity<>(EMAIL_WAS_SENT, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(EMAIL_NOT_REGISTERED, HttpStatus.BAD_REQUEST));
    }

    @RequestMapping(value = "/account/reset_password/finish",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> finishPasswordReset(@RequestBody Map<String, String> body) {
        String key = body.get("key");
        String password = body.get("password");

        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>(INCORRECT_PASSWORD, HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(password, key)
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private static boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
            password.length() >= UserAccountForm.PASSWORD_MIN_LENGTH &&
            password.length() <= UserAccountForm.PASSWORD_MAX_LENGTH);
    }

    @RequestMapping(value = "/account",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete() {
        userService.deleteAccount();
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
