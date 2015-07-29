package zero.easymvc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TokenizedParameterTests extends EasyMVCTest {

    public static class Bean {
        @FlagParameter(token = "--token")
        public boolean token;
    }

    public static class Command {
        @CommandHandler(path = { "command" })
        public void execute(Bean bean) {

        }
    }

    public static class Renderer {

        @zero.easymvc.Renderer
        public void render(Bean bean) {

        }
    }

    @Test
    public void should_inject_flag_parameter() throws EasyMVCException {
        controller.registerCommandHandler(Command.class);

        controller.bindPathToRenderer(Renderer.class, new StringArrayCommand("command"));

        Bean bean = (Bean) controller.run("command", "--token");

        assertTrue(bean.token);
    }
    
    @Test
    public void should_run_without_optional_parameter() throws EasyMVCException {
        controller.registerCommandHandler(Command.class);

        controller.bindPathToRenderer(Renderer.class, new StringArrayCommand("command"));

        Bean bean = (Bean) controller.run("command");

        assertFalse(bean.token);
    }
}
