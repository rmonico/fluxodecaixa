package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class EasyMVCFindAndRunCommandTests extends EasyMVCTest {

    public static class TestBean {
        public boolean commandRan;
        public boolean rendererRan;
    }

    public static class TestCommand {

        @Bean
        private TestBean bean;

        @CommandHandler(path = { "command", "subcommand" })
        public void execute() {
            bean = new TestBean();

            bean.commandRan = true;
        }

    }

    public static class TestRenderer {

        private TestBean bean;

        @Renderer
        public void render() {
            bean.rendererRan = true;
        }
    }

    @Test
    public void should_find_and_run_command() throws EasyMVCException {
        controller.registerCommandHandler(TestCommand.class);

        controller.bindPathToRenderer(TestRenderer.class, new StringArrayCommand("command", "subcommand"));

        List<Object> beans = controller.run("command", "subcommand");

        assertNotNull(beans);
        assertEquals(1, beans.size());

        assertEquals(TestBean.class, beans.get(0).getClass());

        TestBean bean = (TestBean) beans.get(0);

        assertNotNull(bean);
        assertTrue(bean.commandRan);
        assertTrue(bean.rendererRan);
    }

    @Test(expected = EasyMVCException.class)
    public void should_throw_exception_on_invoking_unknown_command() throws EasyMVCException {
        controller.run("unknown", "command");
    }

    @Test(expected = EasyMVCException.class)
    public void should_throw_exception_on_renderer_not_found() throws EasyMVCException {
        controller.registerCommandHandler(TestCommand.class);

        controller.run("command", "subcommand");
    }

    @Test(expected = EasyMVCException.class)
    public void should_throw_exception_when_calling_command_with_renderer_but_without_handler() throws EasyMVCException {
        controller.bindPathToRenderer(TestRenderer.class, new StringArrayCommand("command"));

        controller.run("command");
    }

    public static class InvalidCommand {

        @CommandHandler(path = "command")
        public void run(Object parameter) {

        }
    }

    @Test(expected = RuntimeException.class)
    public void command_handler_cannot_have_params() throws EasyMVCException {
        controller.registerCommandHandler(InvalidCommand.class);
    }

}
