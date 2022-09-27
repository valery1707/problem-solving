package name.valery1707.problem;

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

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
public class FibBenchmark {

    @Param({"10", "30"})
    private int n;

    @Benchmark
    public void fibClassic(Blackhole bh) {
        bh.consume(Fib.fibClassic(n));
    }

    @Benchmark
    public void fibTailRec(Blackhole bh) {
        bh.consume(Fib.tailRecFib(n));
    }

}
