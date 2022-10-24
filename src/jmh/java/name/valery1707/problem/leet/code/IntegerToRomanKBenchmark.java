package name.valery1707.problem.leet.code;

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
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class IntegerToRomanKBenchmark {

    @Param({"1994", "1776"})
    private Integer integer;

    @Param
    private IntegerToRomanK.Implementation variant;

    @Benchmark
    public void benchmark(Blackhole bh) {
        bh.consume(variant.intToRoman(integer));
    }

}
