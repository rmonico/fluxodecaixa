package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import zero.easymvc.EasyMVC;
import zero.easymvc.EasyMVCException;
import zero.easymvc.StringArrayCommand;
import zero.fluxodecaixa.app.ContaLsBean;
import zero.fluxodecaixa.app.ContaLsCommand;
import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ContaLsTest {

    private ConnectionManager connectionManager;

    private IDatabaseConnection getConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class.forName(ConnectionManager.DRIVER_CLASS);
        Connection jdbcConnection = DriverManager.getConnection(ConnectionManager.CONNECTION_STRING);

        return new DatabaseConnection(jdbcConnection);
    }

    private IDataSet getDataSet() throws DataSetException, FileNotFoundException {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("dbunit/dbunit_dataset.xml"));
    }

    @Before
    public void initializeDatabase() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        recreateStructure();
        startDBUnit();
    }

    private void recreateStructure() throws SQLException {
        connectionManager = new ConnectionManager();

        ConnectionSource source = connectionManager.getInstance();

        TableUtils.dropTable(source, Conta.class, true);
        TableUtils.createTable(source, Conta.class);
    }

    public void startDBUnit() throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException {
        IDatabaseConnection connection = getConnection();

        IDataSet dataSet = getDataSet();

        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    @Test
    public void contaLsCommand_should_return_all_available_contas() throws SQLException, EasyMVCException {
        EasyMVC controller = new EasyMVC();

        controller.addDependencyManager(connectionManager);

        controller.registerCommandHandler(ContaLsCommand.class);

        controller.bindPathToRenderer(ContaLsRenderer.class, new StringArrayCommand("conta", "ls"));

        ContaLsBean bean = (ContaLsBean) controller.run("conta", "ls");

        List<Conta> contas = bean.getContas();

        assertEquals(3, contas.size());

        assertConta("carteita", true, contas.get(0));
        assertConta("casa", false, contas.get(1));
        assertConta("itau", true, contas.get(2));
    }

    private void assertConta(String nome, boolean contabilizavel, Conta conta) {
        assertEquals(nome, conta.getNome());
        assertEquals(contabilizavel, conta.isContabilizavel());
    }
}
