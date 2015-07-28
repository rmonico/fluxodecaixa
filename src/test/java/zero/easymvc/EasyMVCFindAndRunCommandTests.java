package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EasyMVCFindAndRunCommandTests {

    public static class TestBean {

        public boolean commandRan;
        public boolean rendererRan;
        public static int instanceCount;

        public TestBean() {
            instanceCount++;
            commandRan = false;
            rendererRan = false;
        }

        public static void reset() {
            instanceCount = 0;
        }

    }

    public static class TestCommand {
        @CommandHandler(path = { "command", "subcommand" })
        public void execute(TestBean bean) {
            bean.commandRan = true;
        }

    }

    public static class TestRenderer {

        public static TestBean bean;

        @Renderer
        public void render(TestBean bean) {
            TestRenderer.bean = bean;
            bean.rendererRan = true;
        }
    }

    @Test(expected = EasyMVCException.class)
    public void should_throw_exception_on_invoking_unknown_command() throws EasyMVCException {
        EasyMVC controller = new EasyMVC();
        TestBean.reset();

        controller.run("unknown", "command");
    }

    @Test(expected = EasyMVCException.class)
    public void should_throw_exception_on_renderer_not_found() throws EasyMVCException {
        EasyMVC controller = new EasyMVC();
        TestBean.reset();

        controller.registerCommandHandler(TestCommand.class);

        controller.run("command", "subcommand");
    }

    public static class InvalidCommand {

        @CommandHandler(path = "command")
        public void run(Object firstBean, Object secondBean) {

        }
    }

    @Test(expected = EasyMVCException.class)
    public void command_should_receive_just_one_bean() throws EasyMVCException {
        EasyMVC controller = new EasyMVC();

        controller.registerCommandHandler(InvalidCommand.class);

        controller.bindPathToRenderer(TestRenderer.class, new StringArrayCommand("command"));

        controller.run("command");
    }

    @Test
    public void should_find_and_run_command() throws EasyMVCException {
        EasyMVC controller = new EasyMVC();
        TestBean.reset();

        controller.registerCommandHandler(TestCommand.class);

        controller.bindPathToRenderer(TestRenderer.class, new StringArrayCommand("command", "subcommand"));

        controller.run("command", "subcommand");

        assertTrue(TestRenderer.bean.commandRan);
        assertTrue(TestRenderer.bean.rendererRan);
        assertEquals(1, TestBean.instanceCount);
    }
}
