package zero.fluxodecaixa;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import zero.easymvc.AbstractEasyMVCTest;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.app.ContaCreateCommand;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.renderer.ContaCreateRenderer;

public class ContaCreateTest extends ContaTest {

    @Before
    public void before() {
        controller.registerCommandHandler(ContaCreateCommand.class);

        controller.registerRenderer(ContaCreateRenderer.class);
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
