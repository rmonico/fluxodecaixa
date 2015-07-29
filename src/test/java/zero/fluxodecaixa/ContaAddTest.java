package zero.fluxodecaixa;

import org.junit.Test;

import zero.easymvc.EasyMVCException;
import zero.easymvc.StringArrayCommand;
import zero.fluxodecaixa.app.ContaAddBean;
import zero.fluxodecaixa.app.ContaAddCommand;
import zero.fluxodecaixa.renderer.ContaAddRenderer;

public class ContaAddTest extends ContaTest {

    @Test
    public void should_create_a_new_conta_on_database() throws EasyMVCException {
        controller.registerCommandHandler(ContaAddCommand.class);

        controller.bindPathToRenderer(ContaAddRenderer.class, new StringArrayCommand("conta", "add"));

        ContaAddBean bean = (ContaAddBean) controller.run("conta", "add", "nova conta");

        assertConta("nova conta", false, bean.getConta());
    }
}
