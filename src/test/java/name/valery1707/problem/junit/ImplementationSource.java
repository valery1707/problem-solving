package name.valery1707.problem.junit;

import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(ImplementationArgumentsProvider.class)
public @interface ImplementationSource {

    Class<? extends Enum<?>>[] implementation();

    CsvSource[] csv() default {};

    CsvFileSource[] csvFiles() default {};

    EnumSource[] enums() default {};

    MethodSource[] method() default {};

    ValueSource[] values() default {};

}
