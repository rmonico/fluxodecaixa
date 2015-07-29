package zero.easymvc;

import static org.junit.Assert.assertEquals;
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

        controller.bindPathToRenderer(Renderer.class, new StringArrayCommand("command"));

        List<Object> beans = controller.run("command", "--token");

        assertNotNull(beans);
        assertEquals(1, beans.size());

        assertEquals(Arguments.class, beans.get(0).getClass());

        Arguments bean = (Arguments) beans.get(0);

        assertNotNull(bean);
        assertTrue(bean.token);
    }

    @Test
    public void should_run_without_optional_parameter() throws EasyMVCException {
        controller.registerCommandHandler(Command.class);

        controller.bindPathToRenderer(Renderer.class, new StringArrayCommand("command"));

        List<Object> beans = controller.run("command");

        assertNotNull(beans);
        assertEquals(1, beans.size());

        assertEquals(Arguments.class, beans.get(0).getClass());

        Arguments bean = (Arguments) beans.get(0);

        assertNotNull(bean);

        assertFalse(bean.token);
    }
}
