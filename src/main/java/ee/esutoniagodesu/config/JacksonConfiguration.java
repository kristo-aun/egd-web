package ee.esutoniagodesu.config;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import ee.esutoniagodesu.util.json.CustomDateTimeConverter;
import ee.esutoniagodesu.util.json.CustomLocalDateConverter;
import ee.esutoniagodesu.util.json.CustomLocalDateTimeConverter;
import ee.esutoniagodesu.util.json.CustomLocalTimeConverter;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JodaModule jacksonJodaModule() {
        JodaModule module = new JodaModule();

        //kuupäev ja kellaaeg koos ajavööndiga
        module.addSerializer(DateTime.class, new CustomDateTimeConverter.Serializer());
        module.addDeserializer(DateTime.class, new CustomDateTimeConverter.Deserializer());

        //kuupäev ja kellaaeg ilma ajavööndita
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeConverter.Serializer());
        module.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeConverter.Deserializer());

        //kuupäev ilma ajavööndita
        module.addSerializer(LocalDate.class, new CustomLocalDateConverter.Serializer());
        module.addDeserializer(LocalDate.class, new CustomLocalDateConverter.Deserializer());

        //kellaaeg ilma ajavööndita
        module.addSerializer(LocalTime.class, new CustomLocalTimeConverter.Serializer());
        module.addDeserializer(LocalTime.class, new CustomLocalTimeConverter.Deserializer());

        return module;
    }
}
