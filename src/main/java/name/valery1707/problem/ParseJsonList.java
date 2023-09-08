package name.valery1707.problem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static net.andreinc.mockneat.unit.objects.Filler.filler;
import static net.andreinc.mockneat.unit.types.Ints.ints;

public interface ParseJsonList {

    TypeReference<List<DemoDto>> TYPE_DEMO_LIST = new TypeReference<>() {};

    default Map<String, DemoDto> buildMap(ObjectMapper mapper, String json) throws JsonProcessingException {
        return parseStream(mapper, json).collect(toUnmodifiableMap(DemoDto::getCode, identity()));
    }

    Stream<DemoDto> parseStream(ObjectMapper mapper, String json) throws JsonProcessingException;

    enum Implementation implements ParseJsonList {
        arrayToGenericStream {
            @Override
            public Stream<DemoDto> parseStream(ObjectMapper mapper, String json) throws JsonProcessingException {
                return Stream.of(mapper.readValue(json, DemoDto[].class));
            }
        },
        arrayToListToStream {
            @Override
            @SuppressWarnings("SimplifyStreamApiCallChains")
            public Stream<DemoDto> parseStream(ObjectMapper mapper, String json) throws JsonProcessingException {
                return Arrays.asList(mapper.readValue(json, DemoDto[].class)).stream();
            }
        },
        arrayToArrayStream {
            @Override
            public Stream<DemoDto> parseStream(ObjectMapper mapper, String json) throws JsonProcessingException {
                return Arrays.stream(mapper.readValue(json, DemoDto[].class));
            }
        },
        listToStream {
            @Override
            public Stream<DemoDto> parseStream(ObjectMapper mapper, String json) throws JsonProcessingException {
                return mapper.readValue(json, new TypeReference<List<DemoDto>>() {}).stream();
            }
        },
        listToStreamCached {
            @Override
            public Stream<DemoDto> parseStream(ObjectMapper mapper, String json) throws JsonProcessingException {
                return mapper.readValue(json, TYPE_DEMO_LIST).stream();
            }
        },
    }

    class DemoDto {

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }

    static String generate(ObjectMapper mapper, int count) throws JsonProcessingException {
        List<DemoDto> list = filler(DemoDto::new)
            .setter(DemoDto::setCode, ints().bound(count * 1000).map(String::valueOf))
            .list(count)
            .get();
        return mapper.writeValueAsString(list);
    }

}
