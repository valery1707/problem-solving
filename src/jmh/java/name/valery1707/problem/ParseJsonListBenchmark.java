package name.valery1707.problem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 2)
@Measurement(iterations = 5, time = 5)
@Fork(2)
@BenchmarkMode({Mode.Throughput})
@SuppressWarnings("unused")
public class ParseJsonListBenchmark {

    @Param({"8", "10", "12", "24", "48", "1024"})
    public int count;

    @Param
    public ParseJsonList.Implementation variant;

    private ObjectMapper mapper;
    private String source;

    @Setup
    public void setup() throws JsonProcessingException {
        mapper = new ObjectMapper();
        source = ParseJsonList.generate(mapper, count);
    }

    @Benchmark
    public void benchmark(Blackhole bh) throws JsonProcessingException {
        bh.consume(variant.buildMap(mapper, source));
    }

}
