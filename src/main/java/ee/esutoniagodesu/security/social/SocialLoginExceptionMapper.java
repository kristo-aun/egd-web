package ee.esutoniagodesu.security.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.social.security.SocialAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * An AuthenticationFailureHandler that replaces Spring's
 */
public class SocialLoginExceptionMapper extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(SocialLoginExceptionMapper.class);

    private final static String DELEGATED = "SocialLoginRejectedFailureHandler.delegated";

    protected Map<Class<? extends AuthenticationException>, String> map = new HashMap<>();
    protected SocialAuthenticationFailureHandler delegate = new SocialAuthenticationFailureHandler(this);

    public SocialLoginExceptionMapper(String defaultFailureUrl) {
        log.debug("New SocialLoginExceptionMapper {}", defaultFailureUrl);
        super.setDefaultFailureUrl(defaultFailureUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.debug("onAuthenticationFailure: " + e.getMessage(), e);

        System.out.println("getContextPath " + request.getContextPath());
        System.out.printf("getPathInfo " + request.getPathInfo());
        System.out.println("getHeaderNames " + request.getHeaderNames());
        System.out.printf("getRemoteUser " + request.getRemoteUser());
        System.out.println("request " + request.toString());

        Enumeration<String> a = request.getAttributeNames();
        while (a.hasMoreElements()) {
            System.out.println("nextelement " + a.nextElement());
        }

        if (request.getAttribute(DELEGATED) == null) {
            System.out.println("stsena 1 boot");
            request.setAttribute(DELEGATED, Boolean.TRUE);
            delegate.onAuthenticationFailure(request, response, e);
        }
        else if (map.containsKey(e.getClass())) {
            System.out.println("stsena 2");
            String url = map.get(e.getClass());
            super.getRedirectStrategy().sendRedirect(request, response, url);
        }
        else {
            System.out.println("stsena 3 tomcat");
            super.onAuthenticationFailure(request, response, e);
        }
    }

    public SocialLoginExceptionMapper add(Class<? extends AuthenticationException> clazz, String url) {
        log.debug("add: {}, {}" , clazz, url);
        map.put(clazz, url);
        return this;
    }
}
