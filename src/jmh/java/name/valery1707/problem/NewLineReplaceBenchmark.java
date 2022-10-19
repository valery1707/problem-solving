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

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class NewLineReplaceBenchmark {

    private static final char[] CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '*'};

    @Param({"10", "100", "1000"})
    public int length;

    @Param({"1.0", "0.7", "0.5", "0.1"})
    public double percent;

    @Param
    private NewLineReplace.Implementation variant;

    private String source;

    @Setup
    public void setup() {
        source = IntStream
            .generate(() -> ThreadLocalRandom.current().nextInt(CHARS.length))
            .limit(length)
            .mapToObj(i -> CHARS[i])
            .reduce(new StringBuilder(length), StringBuilder::append, StringBuilder::append)
            .toString();
        int partSize = (int) (length * percent);
        source = IntStream
            .rangeClosed(0, length / partSize)
            .mapToObj(part -> source.substring(part * partSize, Math.min((part + 1) * partSize, length)))
            .collect(joining("\r\n"));
    }

    @Benchmark
    public void benchmark(Blackhole bh) {
        bh.consume(variant.replace(source));
    }

}
