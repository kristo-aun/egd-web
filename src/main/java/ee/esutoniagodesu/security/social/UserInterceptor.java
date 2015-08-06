package ee.esutoniagodesu.security.social;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Before a request is handled:
 * 1. sets the current User in the {@link SecurityContext} from a cookie, if present and the user is still connected to Facebook.
 * 2. requires that the user sign-in if he or she hasn't already.
 * @author Keith Donald
 */
public final class UserInterceptor extends HandlerInterceptorAdapter {

	private final UsersConnectionRepository connectionRepository;
	private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();
    private final SocialUserDetailsService socialUserDetailsService;

	public UserInterceptor(UsersConnectionRepository connectionRepository, SocialUserDetailsService socialUserDetailsService) {
		this.connectionRepository = connectionRepository;
        this.socialUserDetailsService = socialUserDetailsService;
    }

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		rememberUser(request, response);
		handleSignOut(request, response);
		if (SecurityContext.userSignedIn() || requestForSignIn(request)) {
			return true;
		} else {
			return requireSignIn(request, response);
		}
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		SecurityContext.remove();
	}

	// internal helpers

	private void rememberUser(HttpServletRequest request, HttpServletResponse response) {
		String uuid = userCookieGenerator.readCookieValue(request);
		if (uuid == null) {
			return;
		}
		if (!userNotFound(uuid)) {
			//userCookieGenerator.removeCookie(response);
			return;
		}
		SecurityContext.setCurrentUser(socialUserDetailsService.loadUserByUserId(uuid));
	}

	private void handleSignOut(HttpServletRequest request, HttpServletResponse response) {
		if (SecurityContext.userSignedIn() && request.getServletPath().startsWith("/signout")) {
			connectionRepository.createConnectionRepository(SecurityContext.getCurrentUser().getUsername()).removeConnections("facebook");
			userCookieGenerator.removeCookie(response);
			SecurityContext.remove();
		}
	}

	private boolean requestForSignIn(HttpServletRequest request) {
		return request.getServletPath().startsWith("/auth");
	}

	private boolean requireSignIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//new RedirectView("/signin", true).render(null, request, response);
		return false;
	}

	private boolean userNotFound(String userId) {
		// doesn't bother checking a local user database: simply checks if the userId is connected to Facebook
		return connectionRepository.createConnectionRepository(userId).findPrimaryConnection(Google.class) != null;
	}

}
