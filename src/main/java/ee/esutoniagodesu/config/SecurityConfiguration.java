package ee.esutoniagodesu.config;

import ee.esutoniagodesu.security.*;
import ee.esutoniagodesu.security.social.SocialLoginExceptionMapper;
import ee.esutoniagodesu.web.filter.CsrfCookieGeneratorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private Environment env;

    @Inject
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Inject
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Inject
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private RememberMeServices rememberMeServices;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/scripts/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/assets/**")
            .antMatchers("/test/**")
            .antMatchers("/console/**");
    }

    /**
     * Build a configurer that can be applied to an HttpSecurity instance.  When the configurer is applied,
     * Spring Social Security's {@link org.springframework.social.security.SocialAuthenticationFilter}
     * will be added to the HttpSecurity's SecurityFilterChain.
     */
    protected SpringSocialConfigurer buildSpringSocialConfigurer() {
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .ignoringAntMatchers("/websocket/**")
            .and()
            .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .rememberMe()
            .rememberMeServices(rememberMeServices)
            .rememberMeParameter("remember-me")
            .key(env.getProperty("app.security.rememberme.key"))
            .and()
            .formLogin()
            .loginProcessingUrl("/api/authentication")
            .successHandler(ajaxAuthenticationSuccessHandler)
            .failureHandler(ajaxAuthenticationFailureHandler)
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            .permitAll()
            .and()
            .apply(buildSpringSocialConfigurer()).and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .permitAll()
            .and()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .authorizeRequests()
            .antMatchers(permitAll).permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers(permitAdmin).hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/protected/**").authenticated();
    }

    public static final String[] permitAll = {
        "/api/readings",
        "/api/readings/*",
        "/api/git",
        "/api/dict/**",
        "/api/images/*",
        "/api/audio/*",
        "/api/rtk/**",
        "/api/morphology/**",
        "/api/translator",
        "/api/register",
        "/api/activate",
        "/api/authenticate",
        "/api/account/reset_password/init",
        "/api/account/reset_password/finish"
    };

    public static final String[] permitAdmin = {
        "/api/audits/**",
        "/health/**",
        "/configprops/**"
    };

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
