package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class ArgumentsBeanParserTests extends AbstractEasyMVCTest {

    public static class ComplexObject {
        public int value;
    }

    public static class ComplexObjectParser implements BeanParser {

        @Override
        public Object parse(Object value) {
            String stringValue = (String) value;

            ComplexObject object = new ComplexObject();

            object.value = Integer.parseInt(stringValue);

            return object;
        }

    }

    public static class Arguments {
        @PositionalParameter(parser = ComplexObjectParser.class)
        public ComplexObject object;

    }

    public static class Handler {
        @ArgumentBean
        @Bean
        private Arguments args;

        @CommandHandler(path = "command")
        public void run() {

        }
    }

    public static class MyRenderer {
        @SuppressWarnings("unused")
        private Arguments args;

        @Renderer(path = "command")
        public void run() {

        }
    }

    @Test
    public void should_inject_a_complex_object_into_arguments_bean() throws EasyMVCException {
        controller.registerCommandHandler(Handler.class);
        controller.registerRenderer(MyRenderer.class);

        List<Object> beans = controller.run("command", "55");

        assertBeanList(beans, 1);

        Arguments bean = assertAndGetBean(beans, 0, Arguments.class);

        assertEquals(55, bean.object.value);
    }

    public static class BuiltinParsersArguments {
        @PositionalParameter
        private String stringParam;

        @PositionalParameter(after = "stringParam")
        private Integer integerParam;

        @PositionalParameter(after = "integerParam")
        private int primitiveIntParam;

        @PositionalParameter(after = "primitiveIntParam")
        private Double doubleParam;

        @PositionalParameter(after = "doubleParam")
        private double primitiveDoubleParam;

        @PositionalParameter(after = "primitiveDoubleParam")
        private Boolean booleanParam;

        @PositionalParameter(after = "booleanParam")
        private boolean primitiveBooleanParam;
    }

    public static class BuiltinParsersHandler {
        @ArgumentBean
        @Bean
        private BuiltinParsersArguments args;

        @CommandHandler(path = "command")
        public void run() {

        }
    }

    public static class BuildinParsersRenderer {
        @SuppressWarnings("unused")
        private BuiltinParsersArguments args;

        @Renderer(path = "command")
        public void run() {

        }
    }

    @Test
    public void should_inject_using_builtin_parsers() throws EasyMVCException {
        controller.registerCommandHandler(BuiltinParsersHandler.class);
        controller.registerRenderer(BuildinParsersRenderer.class);

        List<Object> beans = controller.run("command", "string param", "101", "99", "3.14", "2.718", "--booleanParam", "--primitiveBooleanParam");

        assertBeanList(beans, 1);

        BuiltinParsersArguments args = assertAndGetBean(beans, 0, BuiltinParsersArguments.class);

        assertEquals("string param", args.stringParam);
        assertEquals(new Integer(101), args.integerParam);
        assertEquals(99, args.primitiveIntParam);
        assertEquals(new Double(3.14), args.doubleParam);
        assertEquals(2.718, args.primitiveDoubleParam, 0d);
        assertTrue(args.booleanParam);
        assertTrue(args.primitiveBooleanParam);

        // Calendar calendarParam = assertAndGetBean(beans, 5, Calendar.class);
        // Calendar expectedCalendar = GregorianCalendar.getInstance();
        // expectedCalendar.set(Calendar.YEAR, 2015);
        // expectedCalendar.set(Calendar.MONTH, 07);
        // expectedCalendar.set(Calendar.DAY_OF_MONTH, 30);
        // expectedCalendar.set(Calendar.HOUR, 19);
        // expectedCalendar.set(Calendar.MINUTE, 18);
        // expectedCalendar.set(Calendar.SECOND, 47);
        // assertEquals(expectedCalendar, calendarParam);

    }
}
