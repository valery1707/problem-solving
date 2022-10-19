package name.valery1707.problem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.valery1707.problem.junit.Implementation;
import name.valery1707.problem.junit.ImplementationSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ParseJsonListTest {

    public static ObjectMapper MAPPER;

    @BeforeAll
    static void beforeAll() throws JsonProcessingException {
        MAPPER = new ObjectMapper();
        MAPPER.writeValueAsString(new ParseJsonList.DemoDto());
    }

    @ParameterizedTest
    @ImplementationSource(
        implementation = ParseJsonList.Implementation.class,
        values = @ValueSource(ints = {
            0,
            8,
            24,
        })
    )
    void testImplementation(Implementation<ParseJsonList> variant, int count) throws JsonProcessingException {
        var json = ParseJsonList.generate(MAPPER, count);
        var actual = variant.get().buildMap(MAPPER, json);
        assertThat(actual).hasSize(count);
    }

    @ParameterizedTest
    @ValueSource(ints = {
        0,
        8,
        24,
    })
    void testGenerator(int count) throws JsonProcessingException {
        assertThat(ParseJsonList.generate(MAPPER, count)).isNotBlank();
    }

}
