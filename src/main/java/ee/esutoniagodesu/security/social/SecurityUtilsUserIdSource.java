package ee.esutoniagodesu.security.social;

import ee.esutoniagodesu.security.SecurityUtils;
import org.springframework.social.UserIdSource;

public class SecurityUtilsUserIdSource implements UserIdSource {
    @Override
    public String getUserId() {
        return SecurityUtils.getCurrentLogin();
    }
}
