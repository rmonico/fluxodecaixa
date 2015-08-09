package zero.fluxodecaixa;

import java.sql.SQLException;
import java.util.Properties;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import zero.easymvc.EasyMVC;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;
import zero.utils.test.DBUnitTest;

public class TestControllerFactory extends ControllerFactory {
    private String datasetFileName;

    public TestControllerFactory(Properties props, String datasetFileName) {
        super(props);

        this.datasetFileName = datasetFileName;
    }

    @Override
    public EasyMVC createAndSetupController() throws Exception {
        EasyMVC controller = super.createAndSetupController();

        recreateStructure();

        if (datasetFileName != null) {
            DBUnitTest dbUnitTest = new DBUnitTest(connectionManager.getConnectionString(), connectionManager.getDriverClassName());

            dbUnitTest.initializeDBUnit(datasetFileName);
        }

        return controller;
    }

    private void recreateStructure() throws SQLException {
        ConnectionSource source = connectionManager.getConnection();

        TableUtils.dropTable(source, Conta.class, true);
        TableUtils.dropTable(source, Transacao.class, true);
        TableUtils.dropTable(source, Lancamento.class, true);
        TableUtils.createTable(source, Conta.class);
        TableUtils.createTable(source, Transacao.class);
        TableUtils.createTable(source, Lancamento.class);
    }

}
