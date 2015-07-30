package zero.fluxodecaixa;

import java.util.List;

import org.junit.Test;

import zero.easymvc.AbstractEasyMVCTest;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.app.ContaRemoveCommand;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.renderer.ContaRemoveRenderer;

public class ContaRemoveTest extends ContaTest {

    @Override
    protected String getDatasetFileName() {
        return "dbunit/contals_dataset.xml";
    }

    @Test
    public void should_remove_a_conta() throws EasyMVCException {
        controller.registerCommandHandler(ContaRemoveCommand.class);

        controller.registerRenderer(ContaRemoveRenderer.class);

        List<Object> beans = controller.run("conta", "rm", "carteira");

        AbstractEasyMVCTest.assertBeanList(beans, 1);
        Conta removedConta = AbstractEasyMVCTest.assertAndGetBean(beans, 0, Conta.class);

        assertConta("carteira", true, removedConta);
    }
}
