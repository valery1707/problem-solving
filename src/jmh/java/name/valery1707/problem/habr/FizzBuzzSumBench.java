package name.valery1707.problem.habr;

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
import org.openjdk.jmh.util.NullOutputStream;

import java.io.PrintStream;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class FizzBuzzSumBench {

    @Param({"0"})
    private int min;

    @Param({"1000", "100000"})
    private int max;

    @Param({"10", "20"})
    private int lim;

    @Param
    private FizzBuzzSum.Implementation variant;

    @Benchmark
    public void benchmark(Blackhole bh) {
        try (var out = new PrintStream(new NullOutputStream())) {
            variant.calculate(out, min, max, lim);
            bh.consume(out);
        }
    }

}
