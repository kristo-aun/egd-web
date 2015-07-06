package ee.esutoniagodesu;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

@ComponentScan
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Inject
    private Environment env;

    /**
     * Initializes egd.
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     */
    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
            Collection activeProfiles = Arrays.asList(env.getActiveProfiles());
            if (activeProfiles.contains("dev") && activeProfiles.contains("prod")) {
                log.error("You have misconfigured your application! " +
                    "It should not run with both the 'dev' and 'prod' profiles at the same time.");
            }
        }
    }

    private static Properties secretProperties(String profile) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("config/secret-" + profile + ".yml"));
        yaml.afterPropertiesSet();
        return yaml.getObject();
    }

    private static String[] asCommandLineArgs(Properties properties) {
        String[] args = new String[properties.size()];
        int i = 0;
        for (Map.Entry<Object, Object> set : properties.entrySet()) {
            args[i] = "--" + set.getKey() + "=" + set.getValue();
            i++;
        }
        return args;
    }

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(false);

        String profile = getProfile(args);
        String[] mergedArgs = (String[]) ArrayUtils.addAll(asCommandLineArgs(secretProperties(profile)), args);

        Environment env = app.run(mergedArgs).getEnvironment();

        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
                "Local: \t\thttps://127.0.0.1:{}\n\t" +
                "External: \thttps://{}:{}\n----------------------------------------------------------",
            env.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"));

    }

    private static String envProfile() {
        return System.getenv().get("SPRING_PROFILES_ACTIVE");
    }

    private static String argsProfile(String[] args) {
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        return source.getProperty("spring.profiles.active");
    }

    private static String getProfile(String[] args) {
        String profile = envProfile();
        if (profile == null) {
            profile = argsProfile(args);
        }
        if (profile == null) throw new IllegalStateException("Profile not set");
        return profile;
    }
}
