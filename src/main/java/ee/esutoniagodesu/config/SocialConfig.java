package ee.esutoniagodesu.config;

import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.security.social.SimpleSignInAdapter;
import ee.esutoniagodesu.security.social.SimpleSocialUserDetailsService;
import ee.esutoniagodesu.security.social.SocialConnectionSignUp;
import ee.esutoniagodesu.service.MailService;
import ee.esutoniagodesu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
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
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;
import org.springframework.social.security.SocialUserDetailsService;
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

    @Bean
    public SocialUserDetailsService socialUserDetailsService(UserRepository userRepository) {
        log.debug("New instance of " + SocialUserDetailsService.class);
        return new SimpleSocialUserDetailsService(userRepository);
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

    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        addConnectionFactories(configurer);
    }

    private ConnectionFactoryConfigurer addConnectionFactories(ConnectionFactoryConfigurer registry) {
        registry.addConnectionFactory(facebookConnectionFactory());
        registry.addConnectionFactory(googleConnectionFactory());
        return registry;
    }

    private ConnectionFactoryLocator connectionFactoryLocator() {
        log.debug("New instance of " + ConnectionFactoryLocator.class);
        SocialAuthenticationServiceRegistry registry = new SocialAuthenticationServiceRegistry();
        registry.addConnectionFactory(facebookConnectionFactory());
        registry.addConnectionFactory(googleConnectionFactory());
        return registry;
    }

    private static TextEncryptor textEncryptor() {
        log.debug("New instance of " + TextEncryptor.class);
        return Encryptors.noOpText();
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator, ConnectionSignUp connectionSignUp) {
        return memoryCR(connectionFactoryLocator, connectionSignUp);
    }

    @Bean
    public ConnectionSignUp connectionSignUp(UserRepository userRepository, UserService userService, MailService mailService) {
        log.debug("New instance of " + ConnectionSignUp.class);
        return new SocialConnectionSignUp(userRepository, userService, mailService);
    }

    private UsersConnectionRepository jdbcCR(ConnectionFactoryLocator connectionFactoryLocator, ConnectionSignUp connectionSignUp) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
            dataSource, connectionFactoryLocator, textEncryptor());

        repository.setTablePrefix("ac.");
        repository.setConnectionSignUp(connectionSignUp);
        return repository;
    }

    private UsersConnectionRepository memoryCR(ConnectionFactoryLocator connectionFactoryLocator, ConnectionSignUp connectionSignUp) {
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        repository.setConnectionSignUp(connectionSignUp);
        return repository;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository(UsersConnectionRepository usersConnectionRepository) {
        String uuid = SecurityUtils.getUserUuid();
        return usersConnectionRepository.createConnectionRepository(uuid);
    }

    public UserIdSource getUserIdSource() {
        log.debug("New instance of " + AuthenticationNameUserIdSource.class);
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
                                               ConnectionRepository connectionRepository) {

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

    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        log.debug("New instance of " + SpringSocialConfigurer.class);

        return new SpringSocialConfigurer()
            .postLoginUrl("/#/")
            .alwaysUsePostLoginUrl(true);
    }

    @Bean
    public SignInAdapter signInAdapter(SocialUserDetailsService socialUserDetailsService) {
        log.debug("New instance of " + ConnectionSignUp.class);
        return new SimpleSignInAdapter(socialUserDetailsService);
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator,
                                                             UsersConnectionRepository usersConnectionRepository,
                                                             SignInAdapter signInAdapter) {

        log.debug("New instance of " + ProviderSignInController.class);
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
    }
}
