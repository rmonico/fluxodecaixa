package zero.fluxodecaixa;

import java.util.List;

import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.model.Conta;

public class ContaCreateTest extends FluxodecaixaTest {

    @Test
    public void should_create_a_new_conta() throws EasyMVCException {
        List<Object> beans = controller.run("conta", "add", "nova conta");

        EasyMVCAssert.assertBeanList(beans, 1);

        Conta conta = EasyMVCAssert.assertAndGetBean(beans, 0, Conta.class);

        Assert.assertConta("nova conta", false, conta);
    }

    @Test
    public void should_create_a_new_conta_contabilizavel() throws EasyMVCException {
        List<Object> beans = controller.run("conta", "add", "conta", "--contabilizavel");

        EasyMVCAssert.assertBeanList(beans, 1);

        Conta conta = EasyMVCAssert.assertAndGetBean(beans, 0, Conta.class);

        Assert.assertConta("conta", true, conta);
    }
}
