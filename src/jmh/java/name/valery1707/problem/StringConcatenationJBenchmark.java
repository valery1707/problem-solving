package name.valery1707.problem;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class StringConcatenationJBenchmark {

    @Param({"0", "1", "10", "50"})
    public int lhsLen;

    @Param({"0", "1", "10", "50"})
    public int rhsLen;

    @Param
    private StringConcatenationJ.Implementation variant;

    private String lhs;
    private String rhs;

    @Setup
    public void setup() {
        Random random = ThreadLocalRandom.current();
        lhs = StringConcatenationJ.generate(random, lhsLen);
        rhs = StringConcatenationJ.generate(random, rhsLen);
    }

    @Benchmark
    public void benchmark(Blackhole bh) {
        bh.consume(variant.accept(lhs, rhs));
    }

}
