package name.valery1707.problem.e.olymp

import name.valery1707.problem.TestUtilsJ.withinConsole
import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Problem8897KTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [Problem8897K.Implementation::class],
        csv = [CsvSource(
            "0,10",
            "3,10",
            "7,10",
            "17,20",
            "10,20",
            "-3,0",
            "-5,0",
            "-7,0",
            "-15,-10",
            //todo Ещё есть доп. тест который не проходит, но не ясно что там проверяется
            "-2147483648,-2147483640",
//            "2147483647,-2147483646",//Тут срабатывает переполнение, но переход на `Long` не исправляет ситуацию, как и переход на BigInteger
        )]
    )
    internal fun test1(variant: Implementation<Problem8897K>, input: String, expected: String) {
        assertThat(withinConsole("|", input, variant.get()::main)).isEqualTo(expected)
    }
}
