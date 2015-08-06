package ee.esutoniagodesu.security.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.context.request.NativeWebRequest;

public class SimpleSignInAdapter implements SignInAdapter {

    private static final Logger log = LoggerFactory.getLogger(SimpleSignInAdapter.class);

    private final SocialUserDetailsService socialUserDetailsService;

    public SimpleSignInAdapter(SocialUserDetailsService socialUserDetailsService) {
        this.socialUserDetailsService = socialUserDetailsService;
    }

    @Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        log.debug("signin {}", localUserId);
        SocialUserDetails userDetails = socialUserDetailsService.loadUserByUserId(localUserId);
        Authentication token = new SocialAuthenticationToken(connection, userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
		return null;
	}
}
