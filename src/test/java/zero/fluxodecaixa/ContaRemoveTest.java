package zero.fluxodecaixa;

import java.util.List;

import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.model.Conta;

public class ContaRemoveTest extends FluxodecaixaTest {

    public ContaRemoveTest() {
        super("dbunit/contatests_dataset.xml");
    }

    @Test
    public void should_remove_a_conta() throws EasyMVCException {
        List<Object> beans = controller.run("conta", "rm", "carteira");

        EasyMVCAssert.assertBeanList(beans, 1);
        Conta removedConta = EasyMVCAssert.assertAndGetBean(beans, 0, Conta.class);

        Assert.assertConta("carteira", true, null, removedConta);
    }

    // TODO Test remove a nonexistent conta
}
