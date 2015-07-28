package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EasyMVCTests {

    public static class TestBean {

        public static boolean commandRan;
        public static boolean rendererRan;
        public static int instanceCount;

        public TestBean() {
            instanceCount++;
        }

        public static void reset() {
            instanceCount = 0;
            commandRan = false;
            rendererRan = false;
        }

    }

    public static class TestCommand {
        @CommandHandler(path = { "command", "subcommand" })
        public void execute(TestBean bean) {
            TestBean.commandRan = true;
        }

    }

    public static class TestsRenderer {

        @Renderer
        public void render(TestBean bean) {
            TestBean.rendererRan = true;
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

    @Test
    public void should_find_and_run_command() throws EasyMVCException {
        EasyMVC controller = new EasyMVC();
        TestBean.reset();

        controller.registerCommandHandler(TestCommand.class);

        controller.bindPathToRenderer(TestsRenderer.class, new StringArrayCommand("command", "subcommand"));

        controller.run("command", "subcommand");

        assertTrue(TestBean.commandRan);
        assertTrue(TestBean.rendererRan);
        assertEquals(1, TestBean.instanceCount);
    }
}
