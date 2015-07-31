package zero.fluxodecaixa;

import java.util.List;

import org.junit.Test;

import zero.easymvc.EasyMVCAssert;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.app.ContaRemoveCommand;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.renderer.ContaRemoveRenderer;

public class ContaRemoveTest extends FluxodecaixaTest {

    public ContaRemoveTest(String datasetFileName) {
        super("dbunit/contals_dataset.xml");
    }

    @Test
    public void should_remove_a_conta() throws EasyMVCException {
        controller.registerCommandHandler(ContaRemoveCommand.class);

        controller.registerRenderer(ContaRemoveRenderer.class);

        List<Object> beans = controller.run("conta", "rm", "carteira");

        EasyMVCAssert.assertBeanList(beans, 1);
        Conta removedConta = EasyMVCAssert.assertAndGetBean(beans, 0, Conta.class);

        Assert.assertConta("carteira", true, removedConta);
    }

    // TODO Test remove a nonexistent conta
}
