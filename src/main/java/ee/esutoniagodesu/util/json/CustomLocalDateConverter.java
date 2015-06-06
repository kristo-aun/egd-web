package ee.esutoniagodesu.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

/**
 * Custom Jackson serializer & deserializer for displaying Joda LocalDate objects.
 */
public class CustomLocalDateConverter {

    private static final Logger log = LoggerFactory.getLogger(CustomLocalDateConverter.class);

    public static final String FORMAT = "yyyy-MM-dd";
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT);

    public static class Formatter implements org.springframework.format.Formatter<LocalDate> {

        private final boolean allowEmpty;

        public Formatter(boolean allowEmpty) {
            this.allowEmpty = allowEmpty;
        }

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            log.debug("parse: {}", text);
            if (allowEmpty && !StringUtils.hasText(text)) {
                return null;
            } else {
                return new LocalDate(formatter.parseDateTime(text));
            }
        }

        @Override
        public String print(LocalDate o, Locale locale) {
            log.debug("print: {}", o);
            return o != null ? o.toString(formatter) : "";
        }
    }

    public static class Serializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
            generator.writeString(formatter.print(value));
        }
    }

    public static class Deserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.VALUE_STRING) {
                String str = jp.getText().trim();
                log.debug("deserialize: {}", str);
                return new LocalDate(formatter.parseDateTime(str));
            }
            if (t == JsonToken.VALUE_NUMBER_INT) {
                return new LocalDate(jp.getLongValue());
            }
            throw ctxt.mappingException(handledType());
        }
    }
}
