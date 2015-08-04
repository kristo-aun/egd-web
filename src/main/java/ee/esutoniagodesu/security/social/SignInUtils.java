package ee.esutoniagodesu.security.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class SignInUtils {

    private static final Logger log = LoggerFactory.getLogger(SignInUtils.class);

	/**
	 * Programmatically signs in the user with the given the user ID.
	 */
	public static void signin(String userId) {
        log.debug("Signin {}", userId);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
	}
}
