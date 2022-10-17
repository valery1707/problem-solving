package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class CanPlaceFlowersKTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [CanPlaceFlowersK.Implementation::class],
        csv = [CsvSource(
            delimiter = '|', value = [
                "1,0,0,0,1|1|true",
                "1,0,0,0,1|2|false",

                "0|1|true",
                "1|1|false",

                "0,0|1|true",
                "0,0|2|false",
                "1,0|1|false",
                "0,1|1|false",

                "0,0,0|1|true",
                "0,0,0|2|true",
                "0,0,0|3|false",
                "1,0,0|1|true",
                "0,1,0|1|false",
                "0,0,1|1|true",
                "1,0,1|1|false",
                "1,0,1|1|false",

                "0,0,0,0|1|true",
                "0,0,0,0|2|true",
                "0,0,0,0|3|false",
                "1,0,0,0|1|true",
                "0,1,0,0|1|true",
                "0,0,1,0|1|true",
                "0,0,0,1|1|true",

                "0,0,0,0,0|1|true",
                "0,0,0,0,0|2|true",
                "0,0,0,0,0|3|true",
                "0,0,0,0,0|4|false",
                "1,0,0,0,0|1|true",
                "1,0,0,0,0|2|true",
                "0,1,0,0,0|1|true",
                "0,1,0,0,0|2|false",
                "0,0,1,0,0|1|true",
                "0,0,1,0,0|2|true",
                "0,0,0,1,0|1|true",
                "0,0,0,1,0|2|false",
                "0,0,0,0,1|1|true",
                "0,0,0,0,1|2|true",
                "0,0,0,0,1|3|false",
                "1,0,1,0,0|1|true",
                "1,0,0,1,0|1|false",
                "1,0,0,0,1|1|true",
                "0,1,0,1,0|1|false",

                "1,0,0,0,0,1|1|true",
                "1,0,0,0,0,1|2|false",

                "1,0,0,0,0,0,1|1|true",
                "1,0,0,0,0,0,1|2|true",
                "1,0,0,0,0,0,1|3|false",
            ]
        )]
    )
    internal fun test1(variant: Implementation<CanPlaceFlowersK>, flowerbedRaw: String, n: Int, expected: Boolean) {
        val flowerbed = flowerbedRaw.split(',').map(String::toInt).toIntArray()
        assertThat(variant.get().canPlaceFlowers(flowerbed, n)).isEqualTo(expected)
    }
}
