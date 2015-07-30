package ee.esutoniagodesu.config;

import ee.esutoniagodesu.repository.domain.ac.UserRepository;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.security.UserNotActivatedException;
import ee.esutoniagodesu.security.social.SocialConnectionSignUp;
import ee.esutoniagodesu.security.social.SocialLoginExceptionMapper;
import ee.esutoniagodesu.service.MailService;
import ee.esutoniagodesu.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.ObjectPostProcessor;
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
import org.springframework.social.connect.web.ReconnectFilter;
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
        return new SocialConnectionSignUp(userRepository, userService, mailService);
    }

    private FacebookConnectionFactory facebookConnectionFactory() {
        String key = env.getProperty("spring.social.facebook.clientId");
        String secret = env.getProperty("spring.social.facebook.clientSecret");
        return new FacebookConnectionFactory(key, secret);
    }

    private GoogleConnectionFactory googleConnectionFactory() {
        String key = env.getProperty("spring.social.google.clientId");
        String secret = env.getProperty("spring.social.google.clientSecret");
        return new GoogleConnectionFactory(key, secret);
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(facebookConnectionFactory());
        cfConfig.addConnectionFactory(googleConnectionFactory());
    }

    private TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
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
        return SecurityUtils::getUserUuid;
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);
        controller.setApplicationUrl(env.getProperty("app.url"));
        return controller;
    }

    @Bean
    public DisconnectController disconnectController(UsersConnectionRepository usersConnectionRepository) {
        return new DisconnectController(usersConnectionRepository, env.getProperty("spring.social.facebook.clientSecret"));
    }

    @Bean
    public ReconnectFilter apiExceptionHandler(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
        return new ReconnectFilter(usersConnectionRepository, userIdSource);
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Facebook facebook(ConnectionRepository repository) {
        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
        return connection != null ? connection.getApi() : null;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public Google google(ConnectionRepository repository) {
        Connection<Google> connection = repository.findPrimaryConnection(Google.class);
        return connection != null ? connection.getApi() : null;
    }

    /**
     * Build a configurer that can be applied to an HttpSecurity instance.  When the configurer is applied,
     * Spring Social Security's {@link org.springframework.social.security.SocialAuthenticationFilter}
     * will be added to the HttpSecurity's SecurityFilterChain.
     */
    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {

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
}
