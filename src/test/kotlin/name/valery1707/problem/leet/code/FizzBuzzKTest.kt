package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class FizzBuzzKTest {
    @ParameterizedTest
    @ImplementationSource(
        implementation = [FizzBuzzK.Implementation::class],
        csv = [CsvSource(
            "3,1;2;Fizz",
            "5,1;2;Fizz;4;Buzz",
            "15,1;2;Fizz;4;Buzz;Fizz;7;8;Fizz;Buzz;11;Fizz;13;14;FizzBuzz",
        )]
    )
    internal fun test1(variant: Implementation<FizzBuzzK>, n: Int, expectedRaw: String) {
        val expected = expectedRaw.split(';')
        assertThat(variant.get().fizzBuzz(n)).containsExactlyElementsOf(expected)
    }
}
