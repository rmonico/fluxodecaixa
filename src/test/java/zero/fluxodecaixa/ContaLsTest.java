package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.junit.Test;

import zero.easymvc.EasyMVC;
import zero.easymvc.EasyMVCException;
import zero.easymvc.StringArrayCommand;
import zero.fluxodecaixa.app.ContaLsBean;
import zero.fluxodecaixa.app.ContaLsCommand;
import zero.fluxodecaixa.model.Conta;

public class ContaLsTest {

    private EasyMVC controller;
    private TestConnectionManager connectionManager;

    @Before
    public void initializeDatabase() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        controller = new EasyMVC();

        connectionManager = new TestConnectionManager();

        controller.addDependencyManager(connectionManager);

        connectionManager.initializeDBUnitDataset("dbunit/contals_dataset.xml");
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

    private void assertConta(String nome, boolean contabilizavel, Conta conta) {
        assertEquals(nome, conta.getNome());
        assertEquals(contabilizavel, conta.isContabilizavel());
    }
}
