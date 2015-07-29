package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import zero.easymvc.EasyMVCException;
import zero.easymvc.StringArrayCommand;
import zero.fluxodecaixa.app.ContaLsBean;
import zero.fluxodecaixa.app.ContaLsCommand;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.renderer.ContaLsRenderer;

public class ContaLsTest extends ContaTest {

    @Override
    protected String getDatasetFileName() {
        return "dbunit/contals_dataset.xml";
    }

    @Test
    public void contaLsCommand_should_return_all_available_contas() throws SQLException, EasyMVCException {
        controller.registerCommandHandler(ContaLsCommand.class);

        controller.bindPathToRenderer(ContaLsRenderer.class, new StringArrayCommand("conta", "ls"));

        ContaLsBean bean = (ContaLsBean) controller.run("conta", "ls");

        List<Conta> contas = bean.getContas();

        assertEquals(3, contas.size());

        assertConta("carteira", true, contas.get(0));
        assertConta("casa", false, contas.get(1));
        assertConta("itau", true, contas.get(2));
    }

}
