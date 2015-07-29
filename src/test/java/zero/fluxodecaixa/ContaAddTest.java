package zero.fluxodecaixa;

import org.junit.Before;
import org.junit.Test;

import zero.easymvc.EasyMVCException;
import zero.easymvc.StringArrayCommand;
import zero.fluxodecaixa.app.ContaAddBean;
import zero.fluxodecaixa.app.ContaAddCommand;
import zero.fluxodecaixa.renderer.ContaAddRenderer;

public class ContaAddTest extends ContaTest {

    @Before
    public void before() {
        controller.registerCommandHandler(ContaAddCommand.class);

        controller.bindPathToRenderer(ContaAddRenderer.class, new StringArrayCommand("conta", "add"));
    }

    @Test
    public void should_create_a_new_conta() throws EasyMVCException {
        ContaAddBean bean = (ContaAddBean) controller.run("conta", "add", "nova conta");

        assertConta("nova conta", false, bean.getConta());
    }

    @Test
    public void should_create_a_new_conta_contabilizavel() throws EasyMVCException {
        ContaAddBean bean = (ContaAddBean) controller.run("conta", "add", "conta", "--contabilizavel");

        assertConta("conta", true, bean.getConta());
    }
}
