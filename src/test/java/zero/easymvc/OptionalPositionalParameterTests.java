package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class OptionalPositionalParameterTests extends AbstractEasyMVCTest {

    public static class MyCommandArguments {
        @PositionalParameter(required = false)
        public String parameter;
    }

    public static class MyCommand {
        @ArgumentsBean
        @Bean
        private MyCommandArguments args;

        @CommandHandler(path = "command")
        public void run() {

        }
    }

    public static class MyRenderer {
        @SuppressWarnings("unused")
        private MyCommandArguments args;

        @Renderer(path = "command")
        public void render() {

        }
    }

    @Test
    public void should_run_without_optional_parameter() throws EasyMVCException {
        controller.registerCommandHandler(MyCommand.class);
        controller.registerRenderer(MyRenderer.class);

        List<Object> beans = controller.run("command");

        EasyMVCAssert.assertBeanList(beans, 1);

        EasyMVCAssert.assertAndGetBean(beans, 0, MyCommandArguments.class);
    }

    @Test
    public void should_inject_optional_parameter() throws EasyMVCException {
        controller.registerCommandHandler(MyCommand.class);
        controller.registerRenderer(MyRenderer.class);

        List<Object> beans = controller.run("command", "optional argument");

        EasyMVCAssert.assertBeanList(beans, 1);

        MyCommandArguments args = EasyMVCAssert.assertAndGetBean(beans, 0, MyCommandArguments.class);

        assertEquals("optional argument", args.parameter);
    }

    public static class AnotherCommandArguments {
        @PositionalParameter(required = false)
        public String p1;

        @PositionalParameter(after = "p1", required = false)
        public String p2;

        @PositionalParameter(after = "p2", required = false)
        public String p3;

        @TokenParameter(token = "--token")
        public boolean token;
    }

    public static class AnotherCommand {
        @ArgumentsBean
        @Bean
        private AnotherCommandArguments args;

        @CommandHandler(path = "command")
        public void run() {

        }
    }

    public static class AnotherRenderer {
        @SuppressWarnings("unused")
        private AnotherCommandArguments args;

        @Renderer(path = "command")
        public void render() {

        }
    }

    @Test
    public void should_stop_positional_argument_processing_when_found_special_token() throws EasyMVCException {
        controller.registerCommandHandler(AnotherCommand.class);
        controller.registerRenderer(AnotherRenderer.class);

        List<Object> beans = controller.run("command", "--", "--token");

        AnotherCommandArguments args = EasyMVCAssert.assertAndGetBean(beans, 0, AnotherCommandArguments.class);

        assertNull(args.p1);

        assertNull(args.p2);

        assertNull(args.p3);

        assertTrue(args.token);
    }
}
