package project.config.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class IntegerToBigDecimalConverter extends BidirectionalConverter<Integer, BigDecimal> {

    @Override
    public BigDecimal convertTo(final Integer source, final Type<BigDecimal> destinationType,final MappingContext context) {
        return BigDecimal.valueOf(source);
    }

    @Override
    public Integer convertFrom(final BigDecimal source, final Type<Integer> destinationType, final MappingContext context) {
        return source.intValue();
    }

}
