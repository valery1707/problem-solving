package name.valery1707.problem.habr.freelance;

import name.valery1707.problem.leet.code.IntegerToRomanK;
import name.valery1707.problem.leet.code.RomanToIntegerK;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.binarySearch;
import static java.util.Objects.checkIndex;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * <h1>Разработать калькулятор на Java</h1>
 * <p>
 * Требования:
 * <ol>
 * <li>Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b.
 * Данные передаются в одну строку (смотри пример)!
 * Решения, в которых каждое число и арифметическая операция передаются с новой строки считаются неверными.</li>
 * <li>Калькулятор умеет работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.</li>
 * <li>Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более.
 * На выходе числа не ограничиваются по величине и могут быть любыми.</li>
 * <li>Калькулятор умеет работать только с целыми числами.</li>
 * <li>Калькулятор умеет работать только с арабскими или римскими цифрами одновременно, при вводе пользователем строки вроде 3 + II калькулятор должен выбросить исключение и прекратить свою работу.</li>
 * <li>При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.</li>
 * <li>При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.</li>
 * <li>При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение выбрасывает исключение и завершает свою работу.</li>
 * <li>Результатом операции деления является целое число, остаток отбрасывается.</li>
 * <li>Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль.
 * Результатом работы калькулятора с римскими числами могут быть только положительные числа, если результат работы меньше единицы, выбрасывается исключение</li>
 * </ol>
 * <p>
 * Пример работы программы:
 * <ul>
 * <li></li>
 * Input: 1 + 2 Output: 3
 * Input: VI / III Output: II
 * Input: I - II Output: throws Exception //т.к. в римской системе нет отрицательных чисел
 * Input: I + 1 Output: throws Exception //т.к. используются одновременно разные системы счисления
 * Input: 1 Output: throws Exception //т.к. строка не является математической операцией
 * Input: 1 + 2 + 3 Output: throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)
 * </ul>
 *
 * @see <a href="https://freelance.habr.com/tasks/472595">Заказ</a>
 */
public class Task_472595_Calculator {

    private final Set<Parser<?>> parsers = Stream.concat(
        Stream.of(Operator.values()).<Parser<?>>map(OperatorParser::new),
        Stream.of(OperandParser.values())
    ).collect(toUnmodifiableSet());

    private final Set<? extends Validator> validators;

    public Task_472595_Calculator(Set<? extends Validator> validators) {
        this.validators = validators;
    }

    public Task_472595_Calculator() {
        this(EnumSet.allOf(Validators.class));
    }

    public String calculate(String expression) {
        var items = parse(expression);
        validate(items);
        return calculate(items);
    }

    private String calculate(List<Item> expression) {
        MathContext context = new MathContext(4, RoundingMode.HALF_UP);
        var operands = new Stack<Operand>();
        var operators = new Stack<Operator>();
        for (Item item : expression) {
            if (item instanceof Operand operand) {
                operands.push(operand);
            } else if (item instanceof Operator operator) {
                while (!operators.isEmpty()) {
                    calculate(operators.pop(), operands, context);
                }
                operators.push(operator);
            }
        }
        while (!operators.isEmpty()) {
            calculate(operators.pop(), operands, context);
        }
        return operands.pop().format();
    }

    private void calculate(Operator operator, Stack<Operand> operands, MathContext context) {
        operands.push(calculate(operator, operands.pop(), operands.pop(), context));
    }

    private Operand calculate(Operator operator, Operand operand2, Operand operand1, MathContext context) {
        BigDecimal value = operator.func.apply(operand1.value(), operand2.value(), context);
        return operand1.build(value);
    }

    private void validate(List<Item> items) {
        validators.stream()
            .map(it -> it.apply(items))
            .filter(Objects::nonNull).findFirst()
            .ifPresent(it -> {throw it;});
    }

    private List<Item> parse(String expression) {
        requireNonNull(expression, "Expression is null");
        List<Item> items = new ArrayList<>();

        var len = expression.length();
        var from = 0;
        var to = 0;
        Parser<?> curr = null;

        while (to < len) {
            char c = expression.charAt(to);
            var next = parsers.stream().filter(it -> it.supports(c)).findFirst();
            if (next.isPresent()) {//Switch to something
                if (!next.get().equals(curr)) {//to something different
                    if (curr != null) {//Finish previous parsing
                        items.add(curr.parse(expression.substring(from, to)));
                        from = to;
                    }
                    curr = next.get();
                }
            } else {//to skipping items
                if (curr != null) {//Finish previous parsing
                    items.add(curr.parse(expression.substring(from, to)));
                    from = to;
                }
                curr = null;
                from++;
            }
            to++;
        }
        if (curr != null) {//Finish previous parsing
            items.add(curr.parse(expression.substring(from, to)));
        }

        return items;
    }

    private sealed interface Parser<T extends Item> {

        boolean supports(char c);

        T parse(String value);

    }

    private sealed interface Item {
    }

    private static final class OperatorParser implements Parser<Operator> {

        private final Operator operator;

        public OperatorParser(Operator operator) {
            this.operator = operator;
        }

        @Override
        public boolean supports(char c) {
            return operator.code == c;
        }

        @Override
        public Operator parse(String value) {
            checkIndex(value.length() - 1, 1);
            return operator;
        }

    }

    private enum Operator implements Item {
        ADD('+', (op1, op2, context) -> op1.add(op2, context)),
        SUBTRACT('-', (op1, op2, context) -> op1.subtract(op2, context)),
        DIVIDE('/', (op1, op2, context) -> op1.divide(op2, context)),
        MULTIPLY('*', (op1, op2, context) -> op1.multiply(op2, context));

        private final char code;
        private final OperatorFunc func;

        Operator(char code, OperatorFunc func) {
            this.code = code;
            this.func = func;
        }
    }

    @FunctionalInterface
    private interface OperatorFunc {

        BigDecimal apply(BigDecimal op1, BigDecimal op2, MathContext context);

    }

    private enum OperandParser implements Parser<Operand> {
        ARAB("0123456789", ArabOperand::parse),
        ROMAN("IVXLCDM", RomanOperand::parse);

        private final char[] alphabet;
        private final Function<String, Operand> parser;

        OperandParser(String alphabet, Function<String, Operand> parser) {
            this.alphabet = alphabet.toCharArray();
            Arrays.sort(this.alphabet);
            this.parser = parser;
        }


        @Override
        public boolean supports(char c) {
            return binarySearch(alphabet, c) >= 0;
        }

        @Override
        public Operand parse(String value) {
            return parser.apply(value);
        }
    }

    private static sealed abstract class Operand implements Item {

        private final BigDecimal value;

        protected Operand(BigDecimal value) {
            this.value = value;
        }

        public BigDecimal value() {
            return value;
        }

        public abstract String format();

        public abstract Operand build(BigDecimal value);

    }

    private static final class ArabOperand extends Operand {

        public ArabOperand(BigDecimal value) {
            super(value);
        }

        @Override
        public String format() {
            return value().toBigInteger().toString();
        }

        @Override
        public Operand build(BigDecimal value) {
            return new ArabOperand(value);
        }

        public static ArabOperand parse(String value) {
            //todo MathContext
            return new ArabOperand(new BigDecimal(value));
        }

    }

    private static final class RomanOperand extends Operand {

        public RomanOperand(BigDecimal value) {
            super(value);
        }

        @Override
        public String format() {
            int num = value().toBigInteger().intValueExact();
            if (num < 1) {
                throw new CalculationException("Roman numeric system not support negative values");
            }
            //todo Zero
            return IntegerToRomanK.Implementation.simple.intToRoman(num);
        }

        @Override
        public Operand build(BigDecimal value) {
            return new RomanOperand(value);
        }

        public static RomanOperand parse(String value) {
            //todo MathContext?
            return new RomanOperand(new BigDecimal(RomanToIntegerK.Implementation.maps.romanToInt(value)));
        }

    }

    @FunctionalInterface
    private interface Validator extends Function<List<Item>, CalculationException> {
    }

    enum Validators implements Validator {
        CHECK_EMPTY {
            @Override
            public CalculationException apply(List<Item> items) {
                return items.isEmpty() ? new CalculationException("Expression is empty") : null;
            }
        },
        CHECK_OPERATOR_COUNT_MIN {
            @Override
            public CalculationException apply(List<Item> items) {
                return items.stream().anyMatch(Operator.class::isInstance) ? null : new CalculationException("Expression without operator");
            }
        },
        CHECK_OPERATOR_COUNT_MAX {
            @Override
            public CalculationException apply(List<Item> items) {
                return items.stream().filter(Operator.class::isInstance).count() > 1 ? new CalculationException("Expression has too many operators") : null;
            }
        },
        CHECK_OPERATOR_COUNT {
            @Override
            public CalculationException apply(List<Item> items) {
                long operators = items.stream().filter(Operator.class::isInstance).count();
                long operands = items.stream().filter(Operand.class::isInstance).count();
                return (operators != (operands - 1)) ? new CalculationException("Expression has invalid operators count") : null;
            }
        },
        /**
         * Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более.
         */
        CHECK_VALUE_BOUNDS {
            private final BigDecimal MIN = BigDecimal.ONE;
            private final BigDecimal MAX = BigDecimal.TEN;

            @Override
            public CalculationException apply(List<Item> items) {
                return items.stream()
                    .filter(Operand.class::isInstance)
                    .map(Operand.class::cast)
                    .map(Operand::value)
                    .filter(it -> !between(MIN, it, MAX))
                    .findFirst()
                    .map(it -> new CalculationException("Value out of range: " + it))
                    .orElse(null);
            }
        },
        /**
         * Калькулятор умеет работать только с арабскими или римскими цифрами одновременно.
         */
        CHECK_OPERAND_TYPE {
            @Override
            public CalculationException apply(List<Item> items) {
                var types = items.stream()
                    .filter(Operand.class::isInstance)
                    .map(Item::getClass)
                    .collect(toUnmodifiableSet());
                if (types.size() != 1) {
                    return new CalculationException("Expression with several numeric systems");
                } else {
                    return null;
                }
            }
        },
    }

    public static class CalculationException extends RuntimeException {

        public CalculationException(String message) {
            super(message);
        }

    }

    private static <T extends Comparable<T>> boolean between(T min, T val, T max) {
        return val.compareTo(min) >= 0 && val.compareTo(max) <= 0;
    }

}
