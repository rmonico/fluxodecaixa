package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import zero.easymvc.AbstractEasyMVCTest;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.app.ContaListCommand;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.renderer.ContaListRenderer;

public class ContaListTest extends ContaTest {

    @Override
    protected String getDatasetFileName() {
        return "dbunit/contals_dataset.xml";
    }

    @Test
    public void contaLsCommand_should_return_all_available_contas() throws SQLException, EasyMVCException {
        controller.registerCommandHandler(ContaListCommand.class);

        controller.registerRenderer(ContaListRenderer.class);

        List<Object> beans = controller.run("conta", "ls");

        AbstractEasyMVCTest.assertBeanList(beans, 1);
        @SuppressWarnings("unchecked")
        List<Conta> contas = AbstractEasyMVCTest.assertAndGetBean(beans, 0, List.class);

        assertEquals(3, contas.size());

        assertConta("carteira", true, contas.get(0));
        assertConta("casa", false, contas.get(1));
        assertConta("itau", true, contas.get(2));
    }

}