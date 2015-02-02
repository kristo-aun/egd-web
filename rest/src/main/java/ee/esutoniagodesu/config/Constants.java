package ee.esutoniagodesu.config;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Application constants.
 */
public final class Constants {

    private Constants() {
    }

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_FAST = "fast";
    public static final String SYSTEM_ACCOUNT = "system";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("d MMMM yyyy");
}
