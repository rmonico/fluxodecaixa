package zero.easymvc;

import java.util.List;

import org.junit.Before;
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

    @Before
    public void before() {
        super.before();

        controller.registerCommandHandler(MyCommand.class);
        controller.registerRenderer(MyRenderer.class);
    }

    @Test
    public void should_run_without_optional_parameter() throws EasyMVCException {
        List<Object> beans = controller.run("command");

        EasyMVCAssert.assertBeanList(beans, 1);

        EasyMVCAssert.assertAndGetBean(beans, 0, MyCommandArguments.class);
    }

    @Test
    public void should_inject_optional_parameter() {

    }
}
