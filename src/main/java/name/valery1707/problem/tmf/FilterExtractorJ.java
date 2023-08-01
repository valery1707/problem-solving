package name.valery1707.problem.tmf;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.toUpperCase;
import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

/**
 * <h2>4.4 Query Resources with attribute filtering</h2>
 * <p>
 * The following section describes how to retrieve resources using an attribute filtering mechanism.
 * The filtering is based on using name value query parameters on entity attributes.
 * <p>
 * The basic expression is a sequence of attribute assertions being ANDed to formulate a filtering expression:
 * <pre><code>
 * GET \{apiRoot}/\{resourceName}?[\{attributeName}=\{attributeValue}&*]
 * </code></pre>
 * For examples:
 * <pre><code>
 * GET /api/troubleTicket/?status=acknowledged&creationDate=2017-04-20
 * </code></pre>
 * Note that the above expressions match only for attribute value equality.
 * <p>
 * Attribute values OR-ing is supported and is achieved by providing a filtering expression where the same
 * attribute name is duplicated a number of times {@code [{attributeName}={attributeValue}&*]} different values.
 * <p>
 * Alternatively the following expression {@code [{attributeName}={attributeValue},{attributeValue}*]} is also supported.
 * OR-ing can also be explicit by using “;“ many time {@code [{attributeName}={attributeValue};{attributeValue}*]}.
 * <p>
 * The “OR” behavior can also be achieved by providing a filtering expression using one of the following expressions.
 * <p>
 * For example:
 * <ul>
 *   <li>{@code GET /api/troubleTicket?status=acknowledged;status=rejected}</li>
 *   <li>{@code GET /api/troubleTicket?status=acknowledged,rejected}</li>
 * </ul>
 * The following operators may be used:
 * <table>
 * <thead>
 *   <tr><th>Operator literal</th><th>Description</th><th>URL Encoded form</th></tr>
 * </thead>
 * <tbody>
 *   <tr>
 *     <td>.gt &gt;</td>
 *     <td>greater than (&gt;)<br/>Return results where the search criteria field is strictly greater than</td>
 *     <td>%3E</td>
 *   </tr>
 *   <tr>
 *     <td>.gte &gt;=</td>
 *     <td>greater than or equal to (&gt;=)<br/>Return results where the search criteria field is equal of greater than</td>
 *     <td>%3E%3D</td>
 *   </tr>
 *   <tr>
 *     <td>.lt &lt;</td>
 *     <td>less than (&lt;)<br/>Return results where the search criteria field is strictly less than</td>
 *     <td>%3C</td>
 *   </tr>
 *   <tr>
 *     <td>.lte &lt;=</td>
 *     <td>less than or equal to (&lt;=)<br/>Return results where the search criteria field is equal or less than</td>
 *     <td>%3C%3D</td>
 *   </tr>
 *   <tr>
 *     <td>.regex *=</td>
 *     <td>Regexp expression<br/>Note: all regexp special characters must be encoded</td>
 *     <td>%3D~</td>
 *   </tr>
 *   <tr>
 *     <td>.eq</td>
 *     <td>equal to (=)<br/>Returns results where the search criteria fields is equal to</td>
 *     <td>%3D%3D</td>
 *   </tr>
 * </tbody>
 * </table>
 * Examples:
 * <p>
 * Using AND-ed to describe {@code "2017-04-20>dateTime>2013-04-20"}:
 * <pre><code>
 * GET /api/troubleTicket?dateTime%3E2013-04-20&dateTime%3C2017-04-20
 * </code></pre>
 * Using OR-ing to describe {@code "dateTime<2013-04-20 OR dateTime>2017-04-20"}:
 * <pre><code>
 * GET /api/troubleTicket?dateTime%3C2013-04-20;dateTime%3E2017-04-20
 * </code></pre>
 * <p>
 * Complex attribute value type may be filtered using a “.” notation.
 * <pre><code>
 * {empty}[\{attributeName.attributeName}=\{attributeValue}&*]
 * </code></pre>
 * The complete resource representations (with all the attributes) of all the matching entities must be returned.
 * <p>
 * The returned representation of each entity must contain a field called «id» and that field be populated with the resourceID.
 * <p>
 * If the request is successful then the returned code {@code MUST} be {@code 200 OK}.
 * <p>
 * The exceptions code must use the exception codes from <a href="https://www.iana.org/assignments/http-statuscodes/http-status-codes.xml">http-status-codes</a>
 * as explained in section 4.3.
 * <p>
 * Example:
 * <p>
 * Retrieve all Trouble Tickets with dateTime greater than {@code 2013-04-20} and status {@code acknowledged}.
 * <ul>
 *   <li>{@code GET /api/troubleTicket?dateTime.gt=2013-04-20&status=acknowledged}</li>
 *   <li>{@code GET /api/troubleTicket?dateTime%3E2013-04-20&status=acknowledged}</li>
 * </ul>
 *
 * @see <a href="https://www.tmforum.org/resources/specification/tmf630-rest-api-design-guidelines-4-2-0/">TMF630 REST API Design Guidelines 4.2.0</a>
 */
public interface FilterExtractorJ {

    List<String> parseQuery(String query);

    default String urlDecode(String encoded) {
        return URLDecoder.decode(encoded, UTF_8);
    }

    @SuppressWarnings("DuplicatedCode")
    enum Implementation implements FilterExtractorJ {
        naive {
            @Override
            public List<String> parseQuery(String query) {
                List<Filter> filters = new ArrayList<>();
                var step = ParsingStep.FIELD;
                var mode = Mode.AND;
                var operation = Operation.EQ;
                var field = new StringBuilder();
                var value = new StringBuilder();
                for (int i = 0, length = query.length(); i < length; i++) {
                    var curr = query.charAt(i);
                    if (curr == '&' || curr == ';') {
                        //Start next item
                        //todo Valid only in VALUE step
                        filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                        mode = curr == '&' ? Mode.AND : Mode.OR;
                        field.delete(0, field.length());
                        value.delete(0, value.length());
                        step = ParsingStep.FIELD;
                        continue;
                    }
                    if (curr == ',') {
                        //Start next OR variant (preserve all except value and mode)
                        //todo Valid only in VALUE step
                        filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                        mode = Mode.OR;
                        value.delete(0, value.length());
                        continue;
                    }
                    if (curr == '=') {
                        //Start value
                        //todo Valid only in FIELD step
                        step = ParsingStep.VALUE;
                        operation = Operation.EQ;
                        //Extract operation from field suffix
                        var lastDot = field.lastIndexOf(".");
                        if (lastDot > 0) {
                            var suffix = field.substring(lastDot);
                            if (suffix.equalsIgnoreCase(".eq")) {
                                operation = Operation.EQ;
                                field.delete(lastDot, field.length());
                            } else if (suffix.equalsIgnoreCase(".gt")) {
                                operation = Operation.GT;
                                field.delete(lastDot, field.length());
                            } else if (suffix.equalsIgnoreCase(".gte")) {
                                operation = Operation.GTE;
                                field.delete(lastDot, field.length());
                            } else if (suffix.equalsIgnoreCase(".lt")) {
                                operation = Operation.LT;
                                field.delete(lastDot, field.length());
                            } else if (suffix.equalsIgnoreCase(".lte")) {
                                operation = Operation.LTE;
                                field.delete(lastDot, field.length());
                            } else if (suffix.equalsIgnoreCase(".regex")) {
                                operation = Operation.REGEX;
                                field.delete(lastDot, field.length());
                            }
                        }
                        continue;
                    }
                    if (length - i >= 6) {
                        var next = query.substring(i, i + 6);
                        if (next.equalsIgnoreCase("%3E%3D")) {
                            //todo Valid only in FIELD step
                            step = ParsingStep.VALUE;
                            operation = Operation.GTE;
                            i += 5;
                            continue;
                        } else if (next.equalsIgnoreCase("%3C%3D")) {
                            //todo Valid only in FIELD step
                            step = ParsingStep.VALUE;
                            operation = Operation.LTE;
                            i += 5;
                            continue;
                        } else if (next.equalsIgnoreCase("%3D%3D")) {
                            //todo Valid only in FIELD step
                            step = ParsingStep.VALUE;
                            operation = Operation.EQ;
                            i += 5;
                            continue;
                        }
                    }
                    if (length - i >= 4) {
                        var next = query.substring(i, i + 4);
                        if (next.equalsIgnoreCase("%3D~")) {
                            //todo Valid only in FIELD step
                            step = ParsingStep.VALUE;
                            operation = Operation.REGEX;
                            i += 3;
                            continue;
                        }
                    }
                    if (length - i >= 3) {
                        var next = query.substring(i, i + 3);
                        if (next.equalsIgnoreCase("%3E")) {
                            //todo Valid only in FIELD step
                            step = ParsingStep.VALUE;
                            operation = Operation.GT;
                            i += 2;
                            continue;
                        } else if (next.equalsIgnoreCase("%3C")) {
                            //todo Valid only in FIELD step
                            step = ParsingStep.VALUE;
                            operation = Operation.LT;
                            i += 2;
                            continue;
                        }
                    }
                    (switch (step) {
                        case FIELD -> field;
                        case VALUE -> value;
                    }).append(curr);
                }
                if (!value.isEmpty()) {
                    //Finish last item
                    filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                }
                return filters.stream().map(Filter::toString).toList();
            }

            private record Filter(Mode mode, String field, Operation operation, String value) {

                @Override
                public String toString() {
                    return "Filter(%s [%s] %s [%s])".formatted(mode, field, operation, value);
                }

            }
            private enum Mode {
                AND, OR
            }
            private enum Operation {
                GT, GTE,
                LT, LTE,
                REGEX, EQ
            }
            private enum ParsingStep {
                FIELD, VALUE
            }
        },
        maps_builder {
            @Override
            public List<String> parseQuery(String query) {
                List<Filter> filters = new ArrayList<>();
                ParsingStep step = ParsingStep.FIELD;
                Mode mode = Mode.AND;
                Operation operation = Operation.EQ;
                var field = new StringBuilder();
                var value = new StringBuilder();
                for (int i = 0, length = query.length(); i < length; i++) {
                    var curr = query.charAt(i);

                    if (step == ParsingStep.FIELD) {
                        Operation op;
                        if (curr == '=') {
                            //Start value
                            step = ParsingStep.VALUE;
                            operation = Operation.EQ;
                            if (null != (op = Operation.bySuffix(field))) {
                                operation = op;
                                field.delete(field.length() - op.suffix.length(), field.length());
                            }
                        } else if (null != (op = Operation.byEncoded(query, i))) {
                            step = ParsingStep.VALUE;
                            operation = op;
                            i += op.encoded.length() - 1;
                        } else {
                            field.append(curr);
                        }
                    } else /*step == ParsingStep.VALUE*/ {
                        Mode m;
                        if (null != (m = Mode.found(curr))) {
                            filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                            mode = m;
                            field.delete(0, field.length());
                            value.delete(0, value.length());
                            step = ParsingStep.FIELD;
                        } else if (curr == ',') {
                            //Start next OR variant (preserve all except value and mode)
                            filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                            mode = Mode.OR;
                            value.delete(0, value.length());
                        } else {
                            value.append(curr);
                        }
                    }
                }
                if (!value.isEmpty()) {
                    //Finish last item
                    filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                }
                return filters.stream().map(Filter::toString).toList();
            }

            private record Filter(Mode mode, String field, Operation operation, String value) {

                @Override
                public String toString() {
                    return "Filter(%s [%s] %s [%s])".formatted(mode, field, operation, value);
                }

            }

            private enum Mode {
                AND, OR;

                static Mode found(char curr) {
                    return switch (curr) {
                        case '&' -> AND;
                        case ';' -> OR;
                        default -> null;
                    };
                }
            }
            private enum Operation {
                GT(".gt", "%3E"), GTE(".gte", "%3E%3D"),
                LT(".lt", "%3C"), LTE(".lte", "%3C%3D"),
                REGEX(".regex", "%3D~"), EQ(".eq", "%3D%3D");

                private static final Map<String, Operation> BY_SUFFIX = Stream.of(values()).collect(collectingAndThen(
                    toMap(it -> it.suffix, identity(), Objects::requireNonNullElse, () -> new TreeMap<>(CASE_INSENSITIVE_ORDER)),
                    Collections::unmodifiableMap
                ));
                private static final Map<String, Operation> BY_ENCODED = Stream.of(values()).collect(collectingAndThen(
                    toMap(it -> it.encoded, identity(), Objects::requireNonNullElse, () -> new TreeMap<>(
                        comparingInt(String::length).reversed().thenComparing(String::compareTo)
                    )),
                    Collections::unmodifiableMap
                ));

                private final String suffix;
                private final String encoded;

                Operation(String suffix, String encoded) {
                    this.suffix = suffix;
                    this.encoded = encoded;
                }

                public static Operation bySuffix(StringBuilder field) {
                    var lastDot = field.lastIndexOf(".");
                    return lastDot > 0 ? BY_SUFFIX.get(field.substring(lastDot)) : null;
                }

                public static Operation byEncoded(String query, int i) {
                    return BY_ENCODED
                        .entrySet().stream()
                        .filter(it -> startWith(query, i, it.getKey()))
                        .findFirst()
                        .map(Map.Entry::getValue)
                        .orElse(null);
                }

                private static boolean startWith(String base, int i, String search) {
                    if (i + search.length() >= base.length()) {
                        return false;
                    }
                    for (int s = 0, len = search.length(); s < len; s++) {
                        char lhs = base.charAt(i + s);
                        char rhs = search.charAt(s);
                        if (lhs != rhs) {
                            if (isLowerCase(lhs) != isLowerCase(rhs)) {
                                if (toUpperCase(lhs) != toUpperCase(rhs)) {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
            private enum ParsingStep {
                FIELD, VALUE
            }
        },
        switch_builder {
            @Override
            public List<String> parseQuery(String query) {
                List<Filter> filters = new ArrayList<>();
                ParsingStep step = ParsingStep.FIELD;
                Mode mode = Mode.AND;
                Operation operation = Operation.EQ;
                var field = new StringBuilder();
                var value = new StringBuilder();
                for (int i = 0, length = query.length(); i < length; i++) {
                    var curr = query.charAt(i);

                    if (step == ParsingStep.FIELD) {
                        Operation op;
                        if (curr == '=') {
                            //Start value
                            step = ParsingStep.VALUE;
                            operation = Operation.EQ;
                            if (null != (op = Operation.bySuffix(field))) {
                                operation = op;
                                field.delete(field.length() - op.suffixLen, field.length());
                            }
                        } else if (null != (op = Operation.byEncoded(query, i))) {
                            step = ParsingStep.VALUE;
                            operation = op;
                            i += op.encodedLen - 1;
                        } else {
                            field.append(curr);
                        }
                    } else /*step == ParsingStep.VALUE*/ {
                        Mode m;
                        if (null != (m = Mode.found(curr))) {
                            filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                            mode = m;
                            field.delete(0, field.length());
                            value.delete(0, value.length());
                            step = ParsingStep.FIELD;
                        } else if (curr == ',') {
                            //Start next OR variant (preserve all except value and mode)
                            filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                            mode = Mode.OR;
                            value.delete(0, value.length());
                        } else {
                            value.append(curr);
                        }
                    }
                }
                if (!value.isEmpty()) {
                    //Finish last item
                    filters.add(new Filter(mode, urlDecode(field.toString()), operation, urlDecode(value.toString())));
                }
                return filters.stream().map(Filter::toString).toList();
            }

            private record Filter(Mode mode, String field, Operation operation, String value) {

                @Override
                public String toString() {
                    return "Filter(%s [%s] %s [%s])".formatted(mode, field, operation, value);
                }

            }

            private enum Mode {
                AND, OR;

                static Mode found(char curr) {
                    return switch (curr) {
                        case '&' -> AND;
                        case ';' -> OR;
                        default -> null;
                    };
                }
            }
            private enum Operation {
                GT(".gt", "%3E"), GTE(".gte", "%3E%3D"),
                LT(".lt", "%3C"), LTE(".lte", "%3C%3D"),
                REGEX(".regex", "%3D~"), EQ(".eq", "%3D%3D");

                private final int suffixLen;
                private final int encodedLen;

                Operation(String suffix, String encoded) {
                    this.suffixLen = suffix.length();
                    this.encodedLen = encoded.length();
                }

                public static Operation bySuffix(StringBuilder field) {
                    var lastDot = field.lastIndexOf(".");
                    return lastDot <= 0 ? null : switch (field.substring(lastDot)) {
                        case ".eq", ".eQ", ".Eq", ".EQ" -> EQ;
                        case ".gt", ".gT", ".Gt", ".GT" -> GT;
                        case ".lt", ".lT", ".Lt", ".LT" -> LT;
                        case ".gte", ".gtE", ".gTe", ".gTE", ".Gte", ".GtE", ".GTe", ".GTE" -> GTE;
                        case ".lte", ".ltE", ".lTe", ".lTE", ".Lte", ".LtE", ".LTe", ".LTE" -> LTE;
                        case ".regex"
                            , ".regeX", ".regEx", ".regEX", ".reGex", ".reGeX"
                            , ".reGEx", ".reGEX", ".rEgex", ".rEgeX", ".rEgEx"
                            , ".rEgEX", ".rEGex", ".rEGeX", ".rEGEx", ".rEGEX"
                            , ".Regex", ".RegeX", ".RegEx", ".RegEX", ".ReGex"
                            , ".ReGeX", ".ReGEx", ".ReGEX", ".REgex", ".REgeX"
                            , ".REgEx", ".REgEX", ".REGex", ".REGeX", ".REGEx"
                            , ".REGEX" -> REGEX;
                        default -> null;
                    };
                }

                public static Operation byEncoded(String s, int i) {
                    int accessible = s.length() - i;
                    if (s.charAt(i) != '%' || accessible <= 2 || s.charAt(i + 1) != '3') {
                        return null;
                    }
                    if (accessible >= 6 && s.charAt(i + 3) == '%' && s.charAt(i + 4) == '3' && (s.charAt(i + 5) == 'd' || s.charAt(i + 5) == 'D')) {
                        if (s.charAt(i + 2) == 'e' || s.charAt(i + 2) == 'E') {
                            return GTE;
                        } else if (s.charAt(i + 2) == 'c' || s.charAt(i + 2) == 'C') {
                            return LTE;
                        } else if (s.charAt(i + 2) == 'd' || s.charAt(i + 2) == 'D') {
                            return EQ;
                        }
                    }
                    if (accessible >= 4 && s.charAt(i + 3) == '~' && (s.charAt(i + 2) == 'd' || s.charAt(i + 2) == 'D')) {
                        return REGEX;
                    }
                    //accessible >= 3
                    if (s.charAt(i + 2) == 'e' || s.charAt(i + 2) == 'E') {
                        return GT;
                    } else if (s.charAt(i + 2) == 'c' || s.charAt(i + 2) == 'C') {
                        return LT;
                    }
                    return null;
                }
            }
            private enum ParsingStep {
                FIELD, VALUE
            }
        },
        switch_offset {
            @Override
            public List<String> parseQuery(String query) {
                List<Filter> filters = new ArrayList<>();
                ParsingStep step = ParsingStep.FIELD;
                Mode mode = Mode.AND;
                Operation operation = Operation.EQ;
                int fieldS = 0, fieldE = 0;
                int valueS = 0, valueE = 0;
                for (int i = 0, length = query.length(); i < length; i++) {
                    var curr = query.charAt(i);

                    if (step == ParsingStep.FIELD) {
                        Operation op;
                        if (curr == '=') {
                            step = ParsingStep.VALUE;
                            valueS = valueE = i + 1;
                            operation = Operation.EQ;
                            if (null != (op = Operation.bySuffix(query.substring(fieldS, fieldE)))) {
                                operation = op;
                                fieldE -= op.suffixLen;
                            }
                        } else if (null != (op = Operation.byEncoded(query, i))) {
                            step = ParsingStep.VALUE;
                            valueS = valueE = i + op.encodedLen;
                            operation = op;
                            i += op.encodedLen - 1;
                        } else {
                            fieldE++;
                        }
                    } else /*step == ParsingStep.VALUE*/ {
                        Mode m;
                        if (null != (m = Mode.found(curr))) {
                            filters.add(new Filter(mode, urlDecode(query.substring(fieldS, fieldE)), operation, urlDecode(query.substring(valueS, valueE))));
                            mode = m;
                            fieldS = fieldE = i + 1;
                            valueS = valueE = i + 1;
                            step = ParsingStep.FIELD;
                        } else if (curr == ',') {
                            //Start next OR variant (preserve all except value and mode)
                            filters.add(new Filter(mode, urlDecode(query.substring(fieldS, fieldE)), operation, urlDecode(query.substring(valueS, valueE))));
                            mode = Mode.OR;
                            valueS = valueE = i + 1;
                        } else {
                            valueE++;
                        }
                    }
                }
                if (valueE > valueS) {
                    //Finish last item
                    filters.add(new Filter(mode, urlDecode(query.substring(fieldS, fieldE)), operation, urlDecode(query.substring(valueS, valueE))));
                }
                return filters.stream().map(Filter::toString).toList();
            }

            private record Filter(Mode mode, String field, Operation operation, String value) {

                @Override
                public String toString() {
                    return "Filter(%s [%s] %s [%s])".formatted(mode, field, operation, value);
                }

            }

            private enum Mode {
                AND, OR;

                static Mode found(char curr) {
                    return switch (curr) {
                        case '&' -> AND;
                        case ';' -> OR;
                        default -> null;
                    };
                }
            }
            private enum Operation {
                GT(".gt", "%3E"), GTE(".gte", "%3E%3D"),
                LT(".lt", "%3C"), LTE(".lte", "%3C%3D"),
                REGEX(".regex", "%3D~"), EQ(".eq", "%3D%3D");

                private final int suffixLen;
                private final int encodedLen;

                Operation(String suffix, String encoded) {
                    this.suffixLen = suffix.length();
                    this.encodedLen = encoded.length();
                }

                public static Operation bySuffix(String field) {
                    var lastDot = field.lastIndexOf(".");
                    return lastDot <= 0 ? null : switch (field.substring(lastDot)) {
                        case ".eq", ".eQ", ".Eq", ".EQ" -> EQ;
                        case ".gt", ".gT", ".Gt", ".GT" -> GT;
                        case ".lt", ".lT", ".Lt", ".LT" -> LT;
                        case ".gte", ".gtE", ".gTe", ".gTE", ".Gte", ".GtE", ".GTe", ".GTE" -> GTE;
                        case ".lte", ".ltE", ".lTe", ".lTE", ".Lte", ".LtE", ".LTe", ".LTE" -> LTE;
                        case ".regex"
                            , ".regeX", ".regEx", ".regEX", ".reGex", ".reGeX"
                            , ".reGEx", ".reGEX", ".rEgex", ".rEgeX", ".rEgEx"
                            , ".rEgEX", ".rEGex", ".rEGeX", ".rEGEx", ".rEGEX"
                            , ".Regex", ".RegeX", ".RegEx", ".RegEX", ".ReGex"
                            , ".ReGeX", ".ReGEx", ".ReGEX", ".REgex", ".REgeX"
                            , ".REgEx", ".REgEX", ".REGex", ".REGeX", ".REGEx"
                            , ".REGEX" -> REGEX;
                        default -> null;
                    };
                }

                public static Operation byEncoded(String s, int i) {
                    int accessible = s.length() - i;
                    if (s.charAt(i) != '%' || accessible <= 2 || s.charAt(i + 1) != '3') {
                        return null;
                    }
                    char ch2 = switch (s.charAt(i + 2)) {
                        case 'e', 'E' -> 'E';
                        case 'c', 'C' -> 'C';
                        case 'd', 'D' -> 'D';
                        default -> 'X';
                    };
                    if (ch2 == 'X') {
                        return null;
                    }
                    if (accessible >= 6 && s.charAt(i + 3) == '%' && s.charAt(i + 4) == '3' && (s.charAt(i + 5) == 'd' || s.charAt(i + 5) == 'D')) {
                        if (ch2 == 'E') {
                            return GTE;
                        } else if (ch2 == 'C') {
                            return LTE;
                        } else /*if (ch2 == 'D')*/ {
                            return EQ;
                        }
                    }
                    if (accessible >= 4 && ch2 == 'D' && s.charAt(i + 3) == '~') {
                        return REGEX;
                    }
                    //accessible >= 3
                    if (ch2 == 'E') {
                        return GT;
                    } else if (ch2 == 'C') {
                        return LT;
                    }
                    return null;
                }
            }
            private enum ParsingStep {
                FIELD, VALUE
            }
        },
    }

}
