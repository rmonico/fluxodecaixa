package zero.easymvc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParamInjection extends EasyMVCTest {

    public static class Bean {
        @Parameter
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class Handler {
        private static String receivedParam;

        @CommandHandler(path = { "command" })
        public void execute(Bean bean) {
            receivedParam = bean.getName();
        }

        @Renderer
        public void render(Bean bean) {

        }
    }

    @Test(expected = EasyMVCException.class)
    public void should_throw_exception_when_insuficient_params() throws EasyMVCException {
        controller.registerCommandHandler(Handler.class);
        controller.bindPathToRenderer(Handler.class, new StringArrayCommand("command"));

        controller.run("command");
    }

    @Test(expected = EasyMVCException.class)
    public void should_throw_exception_when_extra_params() throws EasyMVCException {
        controller.registerCommandHandler(Handler.class);
        controller.bindPathToRenderer(Handler.class, new StringArrayCommand("command"));

        controller.run("command", "55", "extra argument");
    }

    @Test
    public void should_inject_params_via_annotation() throws EasyMVCException {
        controller.registerCommandHandler(Handler.class);
        controller.bindPathToRenderer(Handler.class, new StringArrayCommand("command"));

        controller.run("command", "55");

        assertEquals("55", Handler.receivedParam);
    }
}
