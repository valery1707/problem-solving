package name.valery1707.problem.leet.code

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import name.valery1707.problem.leet.code.CountCompleteTreeNodesJ.TreeNode
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.pow

internal class CountCompleteTreeNodesJTest {

    @Test
    internal fun testProvidedClass() {
        assertThat(TreeNode()).isNotNull
        assertThat(TreeNode(1, null, null)).isNotNull
    }

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
                "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21];21",
            ]
        )]
    )
    internal fun test1(variant: Implementation<CountCompleteTreeNodesJ>, itemsRaw: String, expected: Int) {
        val items = itemsRaw.trim('[', ']').split(',').filterNot(String::isEmpty).map(String::toInt).toIntArray()
        val root = CountCompleteTreeNodesJ.build(items)
        assertThat(variant.get().countNodes(root)).isEqualTo(expected)
    }

    @ParameterizedTest(name = "[{index}] {0}([...]) == {2}")
    @ImplementationSource(
        implementation = [CountCompleteTreeNodesJ.Implementation::class],
        csv = [CsvSource(
            //The last line is not filled with so many elements
            "5,-1,30",
            //Last line is fully complete
            "5,0,31",
            //The last line is filled with so many elements
            "5,+1,32",
            //Some other tests
            "4,+1,16",
            "4,+2,17",
            "4,+3,18",
            "4,+4,19",
            "4,+5,20",
            "4,+6,21",
            "4,+7,22",
            "4,+8,23",
            "4,+9,24",
            "4,+10,25",
            "4,+11,26",
            "4,+12,27",
            "4,+13,28",
            "4,+14,29",
            "4,+15,30",
            "4,+16,31",
            //Big tree
            "15,+17,32784",
        )]
    )
    internal fun test2(variant: Implementation<CountCompleteTreeNodesJ>, height: Int, lastLineMod: Int, expected: Int) {
        val root = CountCompleteTreeNodesJ.build(height, lastLineMod)
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
        "4,+1,5 -> 16",
        "4,+2,5 -> 17",
        "4,+3,5 -> 18",
        "4,+4,5 -> 19",
        "4,+5,5 -> 20",
        "4,+6,5 -> 21",
        "4,+7,5 -> 22",
        "4,+8,5 -> 23",
        "4,+9,5 -> 24",
        "4,+10,5 -> 25",
        "4,+11,5 -> 26",
        "4,+12,5 -> 27",
        "4,+13,5 -> 28",
        "4,+14,5 -> 29",
        "4,+15,5 -> 30",
        "4,+16,5 -> 31",
        //Big tree
        "15,+17,16 -> 32784",
    )
    internal fun build2(height: Int, lastLineMod: Int, expectedLastItem: String) {
        fun TreeNode?.flatten(level: Int = 1): Sequence<Pair<TreeNode, Int>> = when (this) {
            null -> emptySequence()
            else -> sequenceOf(this to level) + this.left.flatten(level + 1) + this.right.flatten(level + 1)
        }

        val root = CountCompleteTreeNodesJ.build(height, lastLineMod)
        assertThat(root).isNotNull

        val valueByLevel = root.flatten().groupBy({ it.second }, { it.first.`val` })

        val maxLevel = valueByLevel.size
        assertThat(maxLevel).isCloseTo(height, within(1))

        val last = valueByLevel.values.last().last()
        assertThat("$maxLevel -> $last").isEqualTo(expectedLastItem)

        fun String.padMargin(length: Int, padChar: Char = ' '): String = when {
            length <= this.length -> this
            else -> {
                val padLeft = (length - this.length) / 2
                val padRight = length - this.length - padLeft
                this.padStart(this.length + padLeft, padChar).padEnd(this.length + padLeft + padRight, padChar)
            }
        }

        val maxWidth = last.toString().length + 2
        val rows = valueByLevel.mapValues { row ->
            row.value.joinToString(separator = "") {
                it.toString().padMargin(maxWidth * 2F.pow(maxLevel - row.key).toInt())
            }
        }
        assertThat(rows).isNotEmpty
    }
}
