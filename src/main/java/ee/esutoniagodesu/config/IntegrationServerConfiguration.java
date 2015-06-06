package ee.esutoniagodesu.config;

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

    @Override
    public void setEnvironment(Environment env) {
        this.microsoftTranslatePropertyResolver = new RelaxedPropertyResolver(env, "microsoft.translate-service.");
        microsoftTranslate();
    }

    public void microsoftTranslate() {
        log.info("MicrosoftTranslateService.init");
        String clientId = microsoftTranslatePropertyResolver.getProperty("id");
        String clientSecret = microsoftTranslatePropertyResolver.getProperty("secret");
        Translate.setClientId(clientId);
        Translate.setClientSecret(clientSecret);
    }
}
