package ee.esutoniagodesu.config;

import com.google.api.GoogleAPI;
import com.memetix.mst.translate.Translate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class IntegrationServerConfiguration implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(IntegrationServerConfiguration.class);

    private RelaxedPropertyResolver microsoftTranslatePropertyResolver;
    private RelaxedPropertyResolver googleTranslatePropertyResolver;

    @Override
    public void setEnvironment(Environment env) {
        this.microsoftTranslatePropertyResolver = new RelaxedPropertyResolver(env, "app.translate-service.microsoft.");
        this.googleTranslatePropertyResolver = new RelaxedPropertyResolver(env, "app.translate-service.google.");
        microsoftTranslate();
        googleTranslate();
    }

    public void microsoftTranslate() {
        String clientId = microsoftTranslatePropertyResolver.getProperty("clientId");
        String clientSecret = microsoftTranslatePropertyResolver.getProperty("clientSecret");

        log.info("microsoftTranslate.init: {} {}", clientId, clientSecret);

        Translate.setClientId(clientId);
        Translate.setClientSecret(clientSecret);
    }

    public void googleTranslate() {
        String clientId = googleTranslatePropertyResolver.getProperty("clientId");
        String clientSecret = googleTranslatePropertyResolver.getProperty("clientSecret");

        log.info("googleTranslate.init: {} {}", clientId, clientSecret);

        GoogleAPI.setHttpReferrer(clientId);

        // Set the Google Translate API key
        // See: http://code.google.com/apis/language/translate/v2/getting_started.html
        GoogleAPI.setKey(clientSecret);
    }
}
