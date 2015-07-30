package zero.easymvc;

import java.util.HashMap;
import java.util.Map;

public class BuiltinParsers {

    public static Map<Class<?>, BeanParser> parsers = createParsers();

    private static Map<Class<?>, BeanParser> createParsers() {
        Map<Class<?>, BeanParser> parsers = new HashMap<>();
        
        parsers.put(String.class, new StringParser());
        
        return parsers;
    }
    
    private static class StringParser implements BeanParser {

        @Override
        public String parse(Object value) {
            return value.toString();
        }
        
    }
}
