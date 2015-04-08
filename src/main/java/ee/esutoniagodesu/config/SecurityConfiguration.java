package ee.esutoniagodesu.config;

import ee.esutoniagodesu.security.AuthoritiesConstants;
import ee.esutoniagodesu.security.Http401UnauthorizedEntryPoint;
import ee.esutoniagodesu.security.xauth.TokenProvider;
import ee.esutoniagodesu.security.xauth.XAuthTokenConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenProvider tokenProvider;

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
            .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable().and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()

            .antMatchers("/api/git").permitAll()
            .antMatchers("/api/dict/**").permitAll()
            .antMatchers("/api/images/*").permitAll()
            .antMatchers("/api/audio/*").permitAll()
            .antMatchers("/api/test/**").permitAll()
            .antMatchers("/api/article", "/api/article/*").permitAll()
            .antMatchers("/api/rtk/**").permitAll()
            .antMatchers("/api/stats/**").hasAuthority(AuthoritiesConstants.USER)
            .antMatchers("/api/morphology/**").hasAuthority(AuthoritiesConstants.USER)

            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/protected/**")
            .authenticated()
                //.and()
                //.x509()
                //.userDetailsService(userDetails)
            .and()
            .apply(securityConfigurerAdapter());
    }

    private XAuthTokenConfigurer securityConfigurerAdapter() {
        return new XAuthTokenConfigurer(userDetailsService, tokenProvider);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
