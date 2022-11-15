package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import name.valery1707.problem.leet.code.CountCompleteTreeNodesJ.TreeNode
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class CountCompleteTreeNodesJTest {
    @ParameterizedTest(name = "[{index}] {0}([...]) == {2}")
    @ImplementationSource(
        implementation = [CountCompleteTreeNodesJ.Implementation::class],
        csv = [CsvSource(
            delimiter = ';', value = [
                "[];0",
                "[1];1",
                "[1,2,3,4];4",
                "[1,2,3,4,5];5",
                "[1,2,3,4,5,6];6",
                "[1,2,3,4,5,6,7];7",
                "[1,2,3,4,5,6,7,8];8",
                "[1,2,3,4,5,6,7,8,9,10,11,12,13,14];14",
                "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15];15",
                "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16];16",
                "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17];17",
                "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18];18",
                "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19];19",
                "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];20",
            ]
        )]
    )
    internal fun test1(variant: Implementation<CountCompleteTreeNodesJ>, itemsRaw: String, expected: Int) {
        val items = itemsRaw.trim('[', ']').split(',').filterNot(String::isEmpty).map(String::toInt).toIntArray()
        val root = CountCompleteTreeNodesJ.build(items)
        assertThat(variant.get().countNodes(root)).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        delimiter = ';',
        value = [
            "[];''",
            "[1];1()",
            "[1,2];1(2())",
            "[1,2,3,4,5,6];1(2(4(),5()),3(6()))",
            "[1,2,3,4,5,6,7];1(2(4(),5()),3(6(),7()))",
        ]
    )
    internal fun build1(itemsRaw: String, expected: String) {
        val items = itemsRaw.trim('[', ']').split(',').filterNot(String::isEmpty).map(String::toInt).toIntArray()
        fun TreeNode?.str(): String = when (this) {
            null -> ""
            else -> `val`.toString() + listOfNotNull(left, right).joinToString(separator = ",", prefix = "(", postfix = ")") { it.str() }
        }
        assertThat(CountCompleteTreeNodesJ.build(items).str()).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        //The last line is not filled with so many elements
        "5,-1,5 -> 30",
        //Last line is fully complete
        "5,0,5 -> 31",
        //The last line is filled with so many elements
        "5,+1,6 -> 32",
        //Some other tests
        "4,+3,5 -> 18",
    )
    internal fun build2(height: Int, lastLineMod: Int, expectedLastItem: String) {
        fun TreeNode?.flatten(level: Int = 1): Sequence<Pair<TreeNode, Int>> = when (this) {
            null -> emptySequence()
            else -> sequenceOf(this to level) + this.left.flatten(level + 1) + this.right.flatten(level + 1)
        }

        val root = CountCompleteTreeNodesJ.build(height, lastLineMod)
        assertThat(root).isNotNull

        val valueByLevel = root.flatten().map { it.second to it.first.`val` }.toList().sortedBy { it.first }

        val maxLevel = valueByLevel.maxOf { it.first }
        assertThat(maxLevel).isCloseTo(height, within(1))

        val last = valueByLevel.last()
        assertThat("${last.first} -> ${last.second}").isEqualTo(expectedLastItem)
    }
}
