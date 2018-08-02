package project.config.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class ZonedDateTimeToLocalDateTimeConverter extends BidirectionalConverter<LocalDateTime, ZonedDateTime> {

	@Override
	public LocalDateTime convertFrom(final ZonedDateTime source, final Type<LocalDateTime> destination, final MappingContext context) {
		return source.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
	}

	@Override
	public ZonedDateTime convertTo(final LocalDateTime source, final Type<ZonedDateTime> destination, final MappingContext context) {
		return source.atZone(ZoneOffset.UTC);
	}

}
