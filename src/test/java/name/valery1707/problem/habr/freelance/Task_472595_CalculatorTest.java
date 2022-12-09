package name.valery1707.problem.habr.freelance;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.EnumSet;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NewClassNamingConvention")
class Task_472595_CalculatorTest {

    @ParameterizedTest
    @CsvSource({
        //From the problem statement
        "1 + 2, 3",
        "VI / III, II",
        "I - II, throws Roman numeric system not support negative values", //т.к. в римской системе нет отрицательных чисел
        "I + 1, throws Expression with several numeric systems", //т.к. используются одновременно разные системы счисления
        "1, throws Expression without operator", //т.к. строка не является математической операцией
        "1 + 2 + 3, throws Expression has too many operators", //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)
        //Custom
        "1+2, 3",//Spaces is not required
        ", throws Expression is null",//Expression is null
        "'', throws Expression is empty",//Expression is empty
        "3 + 11, throws Value out of range: 11",
        "0 + 14, throws Value out of range: 0",
        "7 + 7, 14",
        "_1! + [2], 3",//Skip unknown characters
        "1+, throws Expression has invalid operators count",
        //Результатом операции деления является целое число, остаток отбрасывается
        "9/3, 3",
        "8/3, 2",
        "7/3, 2",
        "6/3, 2",
        "5/3, 1",
        "4/3, 1",
        "3/3, 1",
        "2/3, 0",
        "1/3, 0",
        //На выходе числа не ограничиваются по величине и могут быть любыми.
        "9*9, 81",
        "2.5+2.5, throws Expression has invalid operators count",//Калькулятор умеет работать только с целыми числами
    })
    void testWithFullValidation(String expression, String expected) {
        test(new Task_472595_Calculator(), expression, expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "1+2+3|CHECK_OPERATOR_COUNT_MAX|6",
        "1+2*3/4|CHECK_OPERATOR_COUNT_MAX|2",//Приоритет не поддерживается
    })
    void testWithPartialValidation(String expression, String validationExcludesStr, String expected) {
        var validationExcludes = Stream.of(validationExcludesStr.split(","))
            .map(Task_472595_Calculator.Validators::valueOf)
            .collect(toUnmodifiableSet());
        var validation = EnumSet.complementOf(EnumSet.copyOf(validationExcludes));
        test(new Task_472595_Calculator(validation), expression, expected);
    }

    private static void test(Task_472595_Calculator calculator, String expression, String expected) {
        if (expected.startsWith("throws")) {
            assertThatThrownBy(() -> calculator.calculate(expression))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(expected.substring("throws".length() + 1));
        } else {
            assertThat(calculator.calculate(expression))
                .isEqualTo(expected);
        }
    }

}
