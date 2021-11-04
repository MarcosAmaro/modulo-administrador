package ar.com.unla.api.annotations.validators;

import ar.com.unla.api.annotations.ValueOfEnum;
import com.google.common.base.CaseFormat;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

    private static final String PARAMETER_FIELD_NAME = "basePath";

    private static final Logger logger = LoggerFactory.getLogger(ValueOfEnumValidator.class);

    private List<String> acceptedValues;

    public ValueOfEnumValidator() {
    }

    public void initialize(ValueOfEnum annotation) {
        this.acceptedValues = Stream.of(annotation.enumClass().getEnumConstants()).map(Enum::name)
                .filter((enumName) -> {
                    return !enumName.equals("DEFAULT");
                }).map((enumName) -> {
                    return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, enumName);
                }).collect(Collectors.toList());
    }

    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value != null && !value.toString().isEmpty()) {
            context.disableDefaultConstraintViolation();
            String message = this.getMessage(context);
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            List<String> lowerAcceptedValues = this.acceptedValues.stream().map(String::toLowerCase)
                    .collect(Collectors.toList());
            return lowerAcceptedValues.contains(value.toString().toLowerCase());
        } else {
            return true;
        }
    }

    private String getMessage(ConstraintValidatorContext context) {
        String parameterName;
        try {
            Field field = context.getClass().getDeclaredField("basePath");
            field.setAccessible(true);
            String node = field.get(context).toString();
            int lastNodeIndex = node.lastIndexOf(46);
            if (lastNodeIndex != -1) {
                parameterName = node.substring(lastNodeIndex + 1);
            } else {
                parameterName = node;
            }
        } catch (IllegalAccessException | NoSuchFieldException var6) {
            logger.error("Error al validar enum en la request", var6);
            throw new RuntimeException("Exception trying to get the enum value to validate it");
        }

        return String.format("El parámetro %s es inválido. Los valores permitidos son: %s",
                parameterName, this.acceptedValues);
    }
}
