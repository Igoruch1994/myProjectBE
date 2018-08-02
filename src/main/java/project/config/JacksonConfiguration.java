package project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import project.util.converters.JSR310LocalDateDeserializer;
import project.util.converters.JSR310LocalDateTimeDeserializer;
import project.util.converters.JSR310LocalDateTimeSerializer;

import java.time.*;
import java.util.Collections;

@Configuration
public class JacksonConfiguration {

    @Bean
    @Primary
    public HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(OffsetDateTime.class, JSR310LocalDateTimeSerializer.INSTANCE);
        module.addSerializer(ZonedDateTime.class, JSR310LocalDateTimeSerializer.INSTANCE);
        module.addSerializer(LocalDateTime.class, JSR310LocalDateTimeSerializer.INSTANCE);
        module.addSerializer(Instant.class, JSR310LocalDateTimeSerializer.INSTANCE);
        module.addDeserializer(LocalDate.class, JSR310LocalDateDeserializer.INSTANCE);
        module.addDeserializer(LocalDateTime.class, new JSR310LocalDateTimeDeserializer());
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .findModulesViaServiceLoader(true)
                .modulesToInstall(module);
        converter.setObjectMapper(jackson2ObjectMapperBuilder.build());
        converter.setObjectMapper(new ObjectMapper());
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        return converter;
    }
}
