package zero.easymvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class ParamInjection extends AbstractEasyMVCTest {

    public static class MyArgumentBean {
        @PositionalParameter
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class Handler {

        @ArgumentBean
        private MyArgumentBean bean;

        @Bean
        private String receivedParam;

        @CommandHandler(path = { "command" })
        public void execute() {
            receivedParam = bean.getName();
        }

        @Renderer
        public void render() {

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

        List<Object> beans = controller.run("command", "55");

        assertNotNull(beans);
        assertEquals(1, beans.size());

        assertEquals("55", beans.get(0));
    }

    public static class TwoParamBean {
        @PositionalParameter(after = "param1")
        public String param2;

        @PositionalParameter
        public String param1;

    }

    public static class Command {

        @ArgumentBean
        @Bean
        private TwoParamBean bean;

        @CommandHandler(path = { "command" })
        public void execute() {

        }

        @Renderer
        public void render() {

        }
    }

    @Test
    public void should_populate_both_params() throws EasyMVCException {
        controller.registerCommandHandler(Command.class);

        controller.bindPathToRenderer(Command.class, new StringArrayCommand("command"));

        List<Object> beans = controller.run("command", "param 1", "param 2");

        assertNotNull(beans);
        assertEquals(1, beans.size());
        assertEquals(TwoParamBean.class, beans.get(0).getClass());

        TwoParamBean bean = (TwoParamBean) beans.get(0);
        assertEquals("param 1", bean.param1);
        assertEquals("param 2", bean.param2);
    }

}
