package ee.esutoniagodesu.config;

import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.security.UserNotActivatedException;
import ee.esutoniagodesu.security.social.SimpleSignInAdapter;
import ee.esutoniagodesu.security.social.SocialConnectionSignUp;
import ee.esutoniagodesu.security.social.SocialLoginExceptionMapper;
import ee.esutoniagodesu.service.MailService;
import ee.esutoniagodesu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ReconnectFilter;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.facebook.web.DisconnectController;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SocialConfig.class);

    @Inject
    private DataSource dataSource;

    @Inject
    private Environment env;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    private ConnectionSignUp connectionSignUp() {
        log.debug("New instance of " + ConnectionSignUp.class);
        return new SocialConnectionSignUp(userRepository, userService, mailService);
    }

    private FacebookConnectionFactory facebookConnectionFactory() {
        log.debug("New instance of " + FacebookConnectionFactory.class);
        String key = env.getProperty("spring.social.facebook.clientId");
        String secret = env.getProperty("spring.social.facebook.clientSecret");
        return new FacebookConnectionFactory(key, secret);
    }

    private GoogleConnectionFactory googleConnectionFactory() {
        log.debug("New instance of " + GoogleConnectionFactory.class);
        String key = env.getProperty("spring.social.google.clientId");
        String secret = env.getProperty("spring.social.google.clientSecret");
        return new GoogleConnectionFactory(key, secret);
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        log.debug("addConnectionFactories");
        cfConfig.addConnectionFactory(facebookConnectionFactory());
        cfConfig.addConnectionFactory(googleConnectionFactory());
    }

    private static TextEncryptor textEncryptor() {
        log.debug("New instance of " + TextEncryptor.class);
        return Encryptors.noOpText();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        log.debug("New instance of " + UsersConnectionRepository.class);
        return memoryCR(connectionFactoryLocator);
    }

    private UsersConnectionRepository jdbcCR(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
            dataSource, connectionFactoryLocator, textEncryptor());

        repository.setTablePrefix("ac.");
        repository.setConnectionSignUp(connectionSignUp());
        return repository;
    }

    private InMemoryUsersConnectionRepository memoryCR(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        repository.setConnectionSignUp(connectionSignUp());
        return repository;
    }

    @Override
    public UserIdSource getUserIdSource() {
        log.debug("New instance of " + UserIdSource.class);
        return SecurityUtils::getUserUuid;
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        log.debug("New instance of " + ConnectController.class);
        ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);
        controller.setApplicationUrl(env.getProperty("app.url"));
        return controller;
    }

    @Bean
    public DisconnectController disconnectController(UsersConnectionRepository usersConnectionRepository) {
        log.debug("New instance of " + DisconnectController.class);
        return new DisconnectController(usersConnectionRepository, env.getProperty("spring.social.facebook.clientSecret"));
    }

    @Bean
    public ReconnectFilter apiExceptionHandler(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
        log.debug("New instance of " + ReconnectFilter.class);
        return new ReconnectFilter(usersConnectionRepository, userIdSource);
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Facebook facebook(ConnectionRepository repository) {
        log.debug("Connection from " + Facebook.class);
        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
        return connection != null ? connection.getApi() : null;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Google google(ConnectionRepository repository) {
        log.debug("Connection from " + Google.class);
        Connection<Google> connection = repository.findPrimaryConnection(Google.class);
        return connection != null ? connection.getApi() : null;
    }

    /**
     * Build a configurer that can be applied to an HttpSecurity instance.  When the configurer is applied,
     * Spring Social Security's {@link org.springframework.social.security.SocialAuthenticationFilter}
     * will be added to the HttpSecurity's SecurityFilterChain.
     */
    @Bean(name = "springSocialConfigurer")
    public SpringSocialConfigurer springSocialConfigurer() {
        log.debug("New instance of " + SpringSocialConfigurer.class);

        // build an AuthenticationFailureHandler that is aware of our own exception types
        final SocialLoginExceptionMapper handler = new SocialLoginExceptionMapper("/#/register/external")
            .add(SocialAuthenticationException.class, "/#/register/external/rejected")
            .add(UserNotActivatedException.class, "/#/activate");

        SpringSocialConfigurer configurer = new SpringSocialConfigurer()
            .postLoginUrl("/#/")
            .alwaysUsePostLoginUrl(true);

        // configure options not available using the standard configurer
        configurer.addObjectPostProcessor(
            new ObjectPostProcessor<SocialAuthenticationFilter>() {
                public SocialAuthenticationFilter postProcess(SocialAuthenticationFilter object) {
                    // replace the default exception
                    object.setAuthenticationFailureHandler(handler);
                    object.setSignupUrl("/#/register/external");
                    return object;
                }
            }
        );

        return configurer;
    }

    @Bean
    public SignInAdapter signInAdapter() {
        log.debug("New instance of " + ConnectionSignUp.class);
        return new SimpleSignInAdapter(new HttpSessionRequestCache());
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator,
                                                             UsersConnectionRepository usersConnectionRepository,
                                                             SignInAdapter signInAdapter) {

        log.debug("New instance of " + ProviderSignInController.class);

        ProviderSignInController providerSigninController =
            new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
        providerSigninController.setSignUpUrl("/#/register-social");
        providerSigninController.setSignInUrl("/#/login");
        return providerSigninController;
    }
}
