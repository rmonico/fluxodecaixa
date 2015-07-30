package zero.fluxodecaixa;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import zero.easymvc.AbstractEasyMVCTest;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.app.ContaAddCommand;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.renderer.ContaAddRenderer;

public class ContaAddTest extends ContaTest {

    @Before
    public void before() {
        controller.registerCommandHandler(ContaAddCommand.class);

        controller.registerRenderer(ContaAddRenderer.class);
    }

    @Test
    public void should_create_a_new_conta() throws EasyMVCException {
        List<Object> beans = controller.run("conta", "add", "nova conta");

        AbstractEasyMVCTest.assertBeanList(beans, 1);

        Conta conta = AbstractEasyMVCTest.assertAndGetBean(beans, 0, Conta.class);

        assertConta("nova conta", false, conta);
    }

    @Test
    public void should_create_a_new_conta_contabilizavel() throws EasyMVCException {
        List<Object> beans = controller.run("conta", "add", "conta", "--contabilizavel");

        AbstractEasyMVCTest.assertBeanList(beans, 1);

        Conta conta = AbstractEasyMVCTest.assertAndGetBean(beans, 0, Conta.class);

        assertConta("conta", true, conta);
    }
}
