package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EasyMVCTests {

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
