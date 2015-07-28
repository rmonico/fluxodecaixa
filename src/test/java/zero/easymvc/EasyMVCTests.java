package zero.easymvc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EasyMVCTests {

    @Test
    public void should_find_and_run_command() {
        EasyMVC controller = new EasyMVC();

        controller.bindPathToRenderer(TestsRenderer.class, "command", "subcommand");

        controller.run("command", "subcommand");

        TestBean bean = (TestBean) controller.getBean();

        assertTrue(bean.isOk());
    }
}
