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

import java.util.stream.IntStream;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class CountCompleteTreeNodesJBenchmark {

    @Param({
        //Small
        "5",
        //Big
        "25",
    })
    private int height;

    @Param({
        //The last line is not filled with so many elements
        "-3",
        //Last line is fully complete
        "0",
        //The last line is filled with so many elements
        "+3",
    })
    private int lastLineMod;

    @Param
    private CountCompleteTreeNodesJ.Implementation variant;

    private CountCompleteTreeNodesJ.TreeNode root;

    @Setup
    public void setup() {
        var size = (int) Math.pow(2, height) + lastLineMod;
        var items = IntStream.rangeClosed(1, size).toArray();
        root = CountCompleteTreeNodesJ.build(items);
    }

    @Benchmark
    public void benchmark(Blackhole bh) {
        bh.consume(variant.countNodes(root));
    }

}
