package ee.esutoniagodesu.service;

import ee.esutoniagodesu.Application;
import ee.esutoniagodesu.config.Constants;
import ee.esutoniagodesu.security.SecurityUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(profiles = {Constants.SPRING_PROFILE_DEV, Constants.SPRING_PROFILE_UNIT})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public abstract class AbstractAuthenticatedServiceTest {

    @Inject
    protected UserDetailsService userDetailsService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    public void login(String username) throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        setUserDetails(userDetails);
    }

    protected void setUserDetails(UserDetails userDetails) {
        String username = userDetails.getUsername();
        TestingAuthenticationToken authenticationToken =
            new TestingAuthenticationToken(userDetails, username, new ArrayList<>(userDetails.getAuthorities()));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);
        boolean isAuthenticated = SecurityUtils.isAuthenticated();
        assertThat(isAuthenticated).isTrue();
    }
}
