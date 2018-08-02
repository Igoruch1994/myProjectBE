package project.config.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateToLocalDateConverter extends BidirectionalConverter<LocalDate, LocalDate> {
	@Override
	public LocalDate convertTo(final LocalDate source, final Type<LocalDate> destinationType, final MappingContext context) {
		return source;
	}

	@Override
	public LocalDate convertFrom(final LocalDate source, final Type<LocalDate> destinationType, final MappingContext context) {
		return source;
	}
}
