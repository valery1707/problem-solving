package name.valery1707.problem.leet.code;

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

import static net.andreinc.mockneat.unit.types.Ints.ints;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class SubarraySumEqualsKJBenchmark {

    @Param({"100", "10000"})
    private int size;

    @Param
    private SubarraySumEqualsKJ.Implementation variant;

    private int[] nums;
    private int k;

    @Setup
    public void setup() {
        nums = ints().rangeClosed(0, 2_000).mapToInt(it -> it - 1_000).arrayPrimitive(size).get();
        k = ints().rangeClosed(0, 10_000_000).mapToInt(it -> it - 10_000_000).get();
    }

    @Benchmark
    public void benchmark(Blackhole bh) {
        bh.consume(variant.subarraySum(nums, k));
    }

}
