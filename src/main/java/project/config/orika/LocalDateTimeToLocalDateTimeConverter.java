package project.config.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeToLocalDateTimeConverter extends BidirectionalConverter<LocalDateTime, LocalDateTime> {
	@Override
	public LocalDateTime convertTo(final LocalDateTime source, final Type<LocalDateTime> destinationType, final MappingContext context) {
		return source;
	}

	@Override
	public LocalDateTime convertFrom(final LocalDateTime source, final Type<LocalDateTime> destinationType, final MappingContext context) {
		return source;
	}
}
