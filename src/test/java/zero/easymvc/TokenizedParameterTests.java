package zero.easymvc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TokenizedParameterTests extends AbstractEasyMVCTest {

    public static class Arguments {
        @FlagParameter(token = "--token")
        public boolean token;
    }

    public static class Command {

        @ArgumentBean
        @Bean
        private Arguments arguments;

        @CommandHandler(path = { "command" })
        public void execute() {

        }
    }

    public static class Renderer {

        @SuppressWarnings("unused")
        private Arguments arguments;

        @zero.easymvc.Renderer
        public void render() {

        }
    }

    @Test
    public void should_inject_flag_parameter() throws EasyMVCException {
        controller.registerCommandHandler(Command.class);

        controller.registerRenderer(Renderer.class);

        List<Object> beans = controller.run("command", "--token");

        assertBeanList(beans, 1);

        Arguments bean = assertAndGetBean(beans, 0, Arguments.class);

        assertNotNull(bean);
        assertTrue(bean.token);
    }

    @Test
    public void should_run_without_optional_parameter() throws EasyMVCException {
        controller.registerCommandHandler(Command.class);

        controller.registerRenderer(Renderer.class);

        List<Object> beans = controller.run("command");

        assertBeanList(beans, 1);

        Arguments bean = assertAndGetBean(beans, 0, Arguments.class);

        assertNotNull(bean);

        assertFalse(bean.token);
    }
}
