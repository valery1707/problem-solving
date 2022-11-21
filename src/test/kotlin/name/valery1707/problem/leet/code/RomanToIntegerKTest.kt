package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class RomanToIntegerKTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [RomanToIntegerK.Implementation::class],
        csv = [CsvSource(
            "III,3",
            "LVIII,58",
            "MCMXCIV,1994",
            "MM,2000",
            "IV,4",
            "IX,9",
            "XL,40",
            "XC,90",
            "CD,400",
            "CM,900",
            "MDCCLXXVI,1776",
        )]
    )
    internal fun test1(variant: Implementation<RomanToIntegerK>, input: String, expected: Int) {
        assertThat(variant.get().romanToInt(input)).isEqualTo(expected)
    }
}
