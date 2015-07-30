package zero.easymvc;

import java.util.HashMap;
import java.util.Map;

public class BuiltinParsers {

    public static Map<Class<?>, BeanParser> parsers = createParsers();

    private static Map<Class<?>, BeanParser> createParsers() {
        Map<Class<?>, BeanParser> parsers = new HashMap<>();

        parsers.put(String.class, new StringParser());

        IntegerParser integerParser = new IntegerParser();
        parsers.put(Integer.class, integerParser);
        parsers.put(int.class, integerParser);

        DoubleParser doubleParser = new DoubleParser();
        parsers.put(Double.class, doubleParser);
        parsers.put(double.class, doubleParser);

        BooleanParser booleanParser = new BooleanParser();
        parsers.put(Boolean.class, booleanParser);
        parsers.put(boolean.class, booleanParser);

        return parsers;
    }

    private static class StringParser implements BeanParser {

        @Override
        public String parse(Object value) {
            return value.toString();
        }

    }

    private static class IntegerParser implements BeanParser {

        @Override
        public Integer parse(Object value) throws BeanParserException {
            // Just support string values now
            String stringValue = convertToString(value);

            return Integer.parseInt(stringValue);
        }

    }

    private static class DoubleParser implements BeanParser {

        @Override
        public Double parse(Object value) throws BeanParserException {
            // Just support string values now
            String stringValue = convertToString(value);

            return Double.parseDouble(stringValue);
        }

    }

    private static class BooleanParser implements BeanParser {

        @Override
        public Boolean parse(Object value) throws BeanParserException {
            // If flag is not passed, its false
            return true;
        }

    }

    private static String convertToString(Object value) throws BeanParserException {
        if (!(value instanceof String))
            throw new BeanParserException("Builtin parsers just support string values.");

        return value.toString();
    }
}
