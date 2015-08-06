package ee.esutoniagodesu;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(ArgumentResolver.class);

    public static String[] secretArgs(String profile) throws UnknownHostException {
        return asCommandLineArgs(secretProperties(profile));
    }

    public static String[] withSecretArgs(String[] args) throws UnknownHostException {
        String profile = getProfile(args);
        String[] mergedArgs = (String[]) ArrayUtils.addAll(secretArgs(profile), args);

        HttpSessionSecurityContextRepository a;
        log.info("Merged arguments {}", Arrays.toString(mergedArgs));
        return mergedArgs;
    }

    public static Properties secretProperties(String profile) {
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
