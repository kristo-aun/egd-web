package ee.esutoniagodesu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import java.util.Properties;

/**
 * This is a helper Java class that provides an alternative to creating a web.xml.
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(ApplicationWebXml.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder app) {
        try {
            String profile = defaultProfile();
            Properties secretProperties = ArgumentResolver.secretProperties(profile);
            log.info("Configure secret: ", secretProperties);

            System.setProperty( "http.proxyPort", "8080" );
            System.setProperty( "https.proxyPort", "8443" );
            System.setProperty( "http.proxyHost", "momo.koodur.com" );
            System.setProperty("https.proxyHost", "momo.koodur.com");

            String proxyHost = System.getProperties().getProperty("http.proxyHost");
            String proxyPort = System.getProperties().getProperty("http.proxyPort");
            log.info("Proxy http: {} {}", proxyHost, proxyPort);

            String proxysHost = System.getProperties().getProperty("https.proxyHost");
            String proxysPort = System.getProperties().getProperty("https.proxyPort");
            log.info("Proxy https: {} {}", proxysHost, proxysPort);

            return app.properties(secretProperties).profiles(profile)
                .showBanner(false)
                .sources(Application.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Please use -Dspring.profiles.active=dev
     */
    private String defaultProfile() {
        String profile = System.getProperty("spring.profiles.active");
        if (profile != null) {
            log.info("Running with Spring profile : {}", profile);
            return profile;
        }
        throw new IllegalStateException("No Spring profile configured");
    }
}
