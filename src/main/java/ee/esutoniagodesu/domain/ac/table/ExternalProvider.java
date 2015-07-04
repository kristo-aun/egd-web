package ee.esutoniagodesu.domain.ac.table;

import org.apache.commons.lang.StringUtils;

/**
 * Supported social providers.  Many other providers are supported, see http://projects.spring.io/spring-social/.
 */
public enum ExternalProvider {
    FACEBOOK,
    GOOGLE,
    LINKEDIN,
    TWITTER;

    public static ExternalProvider caseInsensitiveValueOf(String value) {
        if (StringUtils.isNotBlank(value))
            return ExternalProvider.valueOf(value.toUpperCase());
        else
            return null;
    }
}
