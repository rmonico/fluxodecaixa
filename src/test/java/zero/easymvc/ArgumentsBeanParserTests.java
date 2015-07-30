package zero.easymvc;

import static org.junit.Assert.assertEquals;

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
}
