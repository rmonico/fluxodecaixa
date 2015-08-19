package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

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

        Assert.assertConta("nova conta", false, false, null, conta);
    }

    @Test
    public void should_create_a_new_conta_contabilizavel() throws EasyMVCException {
        List<Object> beans = controller.run("conta", "add", "conta", "--contabilizavel");

        EasyMVCAssert.assertBeanList(beans, 1);

        Conta conta = EasyMVCAssert.assertAndGetBean(beans, 0, Conta.class);

        Assert.assertConta("conta", true, false, null, conta);
    }

    @Test
    public void should_create_a_new_conta_with_observacao() throws EasyMVCException {
        controller.run("conta", "add", "conta", "--observacao=Observação da conta");

        List<Object> beans = controller.run("conta", "ls");

        EasyMVCAssert.assertBeanList(beans, 1);
        @SuppressWarnings("unchecked")
        List<Conta> contas = EasyMVCAssert.assertAndGetBean(beans, 0, List.class);

        assertEquals(1, contas.size());

        Assert.assertConta("conta", false, false, "Observação da conta", contas.get(0));
    }

    @Test
    public void should_create_a_new_saldo_conta() throws EasyMVCException {
        controller.run("conta", "add", "conta", "--saldo");

        List<Object> beans = controller.run("conta", "ls");

        EasyMVCAssert.assertBeanList(beans, 1);
        @SuppressWarnings("unchecked")
        List<Conta> contas = EasyMVCAssert.assertAndGetBean(beans, 0, List.class);

        assertEquals(1, contas.size());

        Assert.assertConta("conta", false, true, null, contas.get(0));
    }
}
