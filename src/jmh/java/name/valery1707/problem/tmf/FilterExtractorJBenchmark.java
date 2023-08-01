package name.valery1707.problem.tmf;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * <pre><code>
 * (query)                (variant)   Mode  Cnt     Score      Error  Units
 *  MEDIUM           naive  thrpt   10  5552,422 ± 7287,394  ops/s
 *  MEDIUM    maps_builder  thrpt   10  2227,318 ±  436,243  ops/s
 *  MEDIUM  switch_builder  thrpt   10  7867,319 ± 3186,571  ops/s
 *  MEDIUM   switch_offset  thrpt   10  9998,258 ± 3270,732  ops/s
 *   LARGE           naive  thrpt   10   840,528 ±  321,186  ops/s
 *   LARGE    maps_builder  thrpt   10   968,454 ±  287,701  ops/s
 *   LARGE  switch_builder  thrpt   10  2415,841 ±  687,946  ops/s
 *   LARGE   switch_offset  thrpt   10  2746,451 ±  511,587  ops/s
 * </code></pre>
 */
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class FilterExtractorJBenchmark {

    public enum Query {
        MEDIUM("@type=GSMCTN&value%3E%3D<CTN_From>&value%3C%3D<CTN_To>" +
            "&resourceCharacteristic[@type%3DNGP].value.ngp=<NGP>" +
            "&resourceCharacteristic[@type%3DSTATUS].value.ctn_status=<CTN_STATUS>'"),
        LARGE("operatorId=one-person,two-person" +
            "&createdAt%3E2013-04-20&createdAt%3C2017-04-20" +
            "&updatedAt.lte=2013-04-20;updatedAt.gte=2017-04-20" +
            "&msisdn.regex=A;msisdn%3d~B;msisdn%3D~C" +
            "&a.gt=1&b.gte=2&c.lt=3&d.lte=4&e.regex=5" +
            "&f.GT=1&g.GTE=2&h.LT=3&i.LTE=4&j.REGEX=5" +
            "&k%3E1&l%3E%3D2&m%3C3&l%3C%3D4&o%3D~5&p%3D%3D6" +
            "&rootAttr.nestedAttr=value" +
            "&resourceCharacteristic[%40type%3DIDS].operator_id=OpOp" +
            "&order=1");

        private final String query;

        Query(String query) {this.query = query;}
    }

    @Param
    private Query query;

    @Param
    private FilterExtractorJ.Implementation variant;

    @Benchmark
    public void benchmark(Blackhole bh) {
        bh.consume(variant.parseQuery(query.query));
    }

}
