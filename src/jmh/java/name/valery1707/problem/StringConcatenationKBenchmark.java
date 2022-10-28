package name.valery1707.problem;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class StringConcatenationKBenchmark {

    @Param({"0", "1", "10", "50"})
    public int lhsLen;

    @Param({"0", "1", "10", "50"})
    public int rhsLen;

    @Param
    private StringConcatenationK.Implementation variant;

    private String lhs;
    private String rhs;

    @Setup
    public void setup() {
        lhs = StringConcatenationJ.generate(lhsLen);
        rhs = StringConcatenationJ.generate(rhsLen);
    }

    @Benchmark
    public void benchmark(Blackhole bh) {
        bh.consume(variant.accept(lhs, rhs));
    }

}
