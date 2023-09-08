package name.valery1707.problem.tmf

import name.valery1707.problem.junit.Implementation
import name.valery1707.problem.junit.ImplementationSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

internal class FilterExtractorJTest {

    @ParameterizedTest(name = "[{index}] {0}({1})")
    @ImplementationSource(
        implementation = [FilterExtractorJ.Implementation::class],
        csv = [
            CsvSource(
                "''" +
                    ",'[]'",

                "'status=acknowledged&creationDate=2017-04-20'" +
                    ",'[" +
                    "Filter(AND [status] EQ [acknowledged]), " +
                    "Filter(AND [creationDate] EQ [2017-04-20])" +
                    "]'",

                "'status=acknowledged;status=rejected'" +
                    ",'[" +
                    "Filter(AND [status] EQ [acknowledged]), " +
                    "Filter(OR [status] EQ [rejected])" +
                    "]'",

                "'status=acknowledged,rejected'" +
                    ",'[" +
                    "Filter(AND [status] EQ [acknowledged]), " +
                    "Filter(OR [status] EQ [rejected])" +
                    "]'",

                "'dateTime%3E2013-04-20&dateTime%3C2017-04-20'" +
                    ",'[" +
                    "Filter(AND [dateTime] GT [2013-04-20]), " +
                    "Filter(AND [dateTime] LT [2017-04-20])" +
                    "]'",

                "'dateTime%3C2013-04-20;dateTime%3E2017-04-20'" +
                    ",'[" +
                    "Filter(AND [dateTime] LT [2013-04-20]), " +
                    "Filter(OR [dateTime] GT [2017-04-20])" +
                    "]'",

                "'dateTime.gt=2013-04-20&status=acknowledged'" +
                    ",'[" +
                    "Filter(AND [dateTime] GT [2013-04-20]), " +
                    "Filter(AND [status] EQ [acknowledged])" +
                    "]'",

                "'dateTime%3E2013-04-20&status=acknowledged'" +
                    ",'[" +
                    "Filter(AND [dateTime] GT [2013-04-20]), " +
                    "Filter(AND [status] EQ [acknowledged])" +
                    "]'",

                "'order%3E1'" +
                    ",'[" +
                    "Filter(AND [order] GT [1])" +
                    "]'",

                //Field contains start of "encoded" comparator `%3`, but has premature EOL
                "'order%3'" +
                    ",'[" +
                    "]'",

                "'order%3E'" +
                    ",'[" +
                    "]'",

                "'value%3C%2B700'" +
                    ",'[" +
                    "Filter(AND [value] LT [+700])" +
                    "]'",

                "'order%30=1'" +
                    ",'[" +
                    "Filter(AND [order0] EQ [1])" +
                    "]'",

                "'order%30%3d%3d1'" +
                    ",'[" +
                    "Filter(AND [order0] EQ [1])" +
                    "]'",
                "'order%30%3D%3D1'" +
                    ",'[" +
                    "Filter(AND [order0] EQ [1])" +
                    "]'",

                "'order%3e%31%37'" +
                    ",'[" +
                    "Filter(AND [order] GT [17])" +
                    "]'",

                "'order%3e%3d%32;order%3c%3d%38;order%3d%3d%35;order%30%3c%36'" +
                    ",'[" +
                    "Filter(AND [order] GTE [2]), " +
                    "Filter(OR [order] LTE [8]), " +
                    "Filter(OR [order] EQ [5]), " +
                    "Filter(OR [order0] LT [6])" +
                    "]'",

                "'ORDER%3E%3D%32;ORDER%3C%3D%38;ORDER%3D%3D%35;ORDER%30%3C%36'" +
                    ",'[" +
                    "Filter(AND [ORDER] GTE [2]), " +
                    "Filter(OR [ORDER] LTE [8]), " +
                    "Filter(OR [ORDER] EQ [5]), " +
                    "Filter(OR [ORDER0] LT [6])" +
                    "]'",

                "'.dot=1'" +
                    ",'[" +
                    "Filter(AND [.dot] EQ [1])" +
                    "]'",

                "'@type=GSMCTN&value%3E%3D<CTN_From>&value%3C%3D<CTN_To>&resourceCharacteristic[@type%3DNGP].value.ngp=<NGP>&resourceCharacteristic[@type%3DSTATUS].value.ctn_status=<CTN_STATUS>'" +
                    ",'[" +
                    "Filter(AND [@type] EQ [GSMCTN]), " +
                    "Filter(AND [value] GTE [<CTN_From>]), " +
                    "Filter(AND [value] LTE [<CTN_To>]), " +
                    "Filter(AND [resourceCharacteristic[@type=NGP].value.ngp] EQ [<NGP>]), " +
                    "Filter(AND [resourceCharacteristic[@type=STATUS].value.ctn_status] EQ [<CTN_STATUS>])" +
                    "]'",

                "'operatorId=one-person,two-person" +
                    "&createdAt%3E2013-04-20&createdAt%3C2017-04-20" +
                    "&updatedAt.lte=2013-04-20;updatedAt.gte=2017-04-20" +
                    "&msisdn.regex=A;msisdn%3d~B;msisdn%3D~C" +
                    "&a.gt=1&b.gte=2&c.lt=3&d.lte=4&e.regex=5" +
                    "&f.GT=1&g.GTE=2&h.LT=3&i.LTE=4&j.REGEX=5" +
                    "&k%3E1&l%3E%3D2&m%3C3&l%3C%3D4&o%3D~5&p%3D%3D6" +
                    "&rootAttr.nestedAttr=value" +
                    "&resourceCharacteristic[%40type%3DIDS].value.operator_id=OpOp" +
                    "&order=1" +
                    "'" +
                    ",'[" +
                    "Filter(AND [operatorId] EQ [one-person]), " +
                    "Filter(OR [operatorId] EQ [two-person]), " +
                    "Filter(AND [createdAt] GT [2013-04-20]), " +
                    "Filter(AND [createdAt] LT [2017-04-20]), " +
                    "Filter(AND [updatedAt] LTE [2013-04-20]), " +
                    "Filter(OR [updatedAt] GTE [2017-04-20]), " +
                    "Filter(AND [msisdn] REGEX [A]), " +
                    "Filter(OR [msisdn] REGEX [B]), " +
                    "Filter(OR [msisdn] REGEX [C]), " +
                    "Filter(AND [a] GT [1]), " +
                    "Filter(AND [b] GTE [2]), " +
                    "Filter(AND [c] LT [3]), " +
                    "Filter(AND [d] LTE [4]), " +
                    "Filter(AND [e] REGEX [5]), " +
                    "Filter(AND [f] GT [1]), " +
                    "Filter(AND [g] GTE [2]), " +
                    "Filter(AND [h] LT [3]), " +
                    "Filter(AND [i] LTE [4]), " +
                    "Filter(AND [j] REGEX [5]), " +
                    "Filter(AND [k] GT [1]), " +
                    "Filter(AND [l] GTE [2]), " +
                    "Filter(AND [m] LT [3]), " +
                    "Filter(AND [l] LTE [4]), " +
                    "Filter(AND [o] REGEX [5]), " +
                    "Filter(AND [p] EQ [6]), " +
                    "Filter(AND [rootAttr.nestedAttr] EQ [value]), " +
                    "Filter(AND [resourceCharacteristic[@type=IDS].value.operator_id] EQ [OpOp]), " +
                    "Filter(AND [order] EQ [1])" +
                    "]'",
            ),
        ],
        method = [
            MethodSource(
                "fieldSuffixesAllCases",
            ),
        ],
    )
    internal fun test1(variant: Implementation<FilterExtractorJ>, query: String, expected: String) {
        assertThat(variant.get().parseQuery(query)).hasToString(expected)
    }

    companion object {
        @JvmStatic
        @Suppress("unused")
        fun fieldSuffixesAllCases(): List<Arguments> {

            fun String.mixCases(): Iterable<String> {
                return (0..1.shl(length)).map { mask ->
                    toCharArray()
                        .mapIndexed { i, c -> if (mask.shr(i) and 1 == 1) c.uppercase() else c.lowercase() }
                        .joinToString(separator = "")
                }
                    .toSet()
            }

            return mapOf(
                "EQ" to ".eq", "REGEX" to ".regex",
                "GT" to ".gt", "LT" to ".lt",
                "GTE" to ".gte", "LTE" to ".lte",
            )
                .mapKeys { (op, _) -> "[Filter(AND [field] $op [value])]" }
                .flatMap { (filter, suffix) ->
                    suffix.mixCases()
                        .map { "field$it=value" }
                        .map { it to filter }
                }
                .map { (query, filter) -> Arguments.of(query, filter) }
        }
    }

}
