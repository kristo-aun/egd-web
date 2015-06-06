package ee.esutoniagodesu.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

/**
 * Custom Jackson serializer & deserializer for displaying Joda DateTime objects.
 */
public class CustomDateTimeConverter {

    private static final Logger log = LoggerFactory.getLogger(CustomDateTimeConverter.class);

    public static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT);

    public static class Formatter implements org.springframework.format.Formatter<DateTime> {

        private final boolean allowEmpty;

        public Formatter(boolean allowEmpty) {
            this.allowEmpty = allowEmpty;
        }

        @Override
        public DateTime parse(String text, Locale locale) throws ParseException {
            log.debug("parse: {}", text);
            if (allowEmpty && !StringUtils.hasText(text)) {
                return null;
            } else {
                return new DateTime(formatter.parseDateTime(text));
            }
        }

        @Override
        public String print(DateTime o, Locale locale) {
            log.debug("print: {}", o);
            return o != null ? o.toString(formatter) : "";
        }
    }

    public static class Serializer extends JsonSerializer<DateTime> {
        @Override
        public void serialize(DateTime value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
            generator.writeString(formatter.print(value));
        }
    }

    public static class Deserializer extends JsonDeserializer<DateTime> {
        @Override
        public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.VALUE_STRING) {
                String str = jp.getText().trim();
                log.debug("deserialize: {}", str);
                return new DateTime(formatter.parseDateTime(str));
            }
            if (t == JsonToken.VALUE_NUMBER_INT) {
                return new DateTime(jp.getLongValue());
            }
            throw ctxt.mappingException(handledType());
        }
    }
}
