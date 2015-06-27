package ee.esutoniagodesu.config;

import ee.esutoniagodesu.util.json.CustomDateTimeConverter;
import ee.esutoniagodesu.util.json.CustomLocalDateConverter;
import ee.esutoniagodesu.util.json.CustomLocalDateTimeConverter;
import ee.esutoniagodesu.util.json.CustomLocalTimeConverter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FormatterConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(FormatterConfiguration.class);

    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.debug("addFormatters : {}", DateTime.class);
        registry.addFormatterForFieldType(DateTime.class, new CustomDateTimeConverter.Formatter(false));

        log.debug("addFormatters : {}", LocalDateTime.class);
        registry.addFormatterForFieldType(LocalDateTime.class, new CustomLocalDateTimeConverter.Formatter(false));

        log.debug("addFormatters : {}", LocalDate.class);
        registry.addFormatterForFieldType(LocalDate.class, new CustomLocalDateConverter.Formatter(false));

        log.debug("addFormatters : {}", LocalTime.class);
        registry.addFormatterForFieldType(LocalTime.class, new CustomLocalTimeConverter.Formatter(false));
    }
}
