package project.util.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public final class JSR310LocalDateTimeSerializer extends JsonSerializer<TemporalAccessor> {

    public static final DateTimeFormatter ISOFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.of("Z"));

    public static final JSR310LocalDateTimeSerializer INSTANCE = new JSR310LocalDateTimeSerializer();

    private JSR310LocalDateTimeSerializer() {
    }

    @Override
    public void serialize(TemporalAccessor value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeString(ISOFormatter.format(value));
    }
}
