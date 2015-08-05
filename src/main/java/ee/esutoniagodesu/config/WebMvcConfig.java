package ee.esutoniagodesu.config;

import ee.esutoniagodesu.security.social.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.inject.Inject;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Inject
    private UsersConnectionRepository usersConnectionRepository;

    @Inject
    private SocialUserDetailsService socialUserDetailsService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new UserInterceptor(usersConnectionRepository, socialUserDetailsService));
    }
}
