package zero.fluxodecaixa;

import java.sql.SQLException;
import java.util.Properties;

import zero.easymvc.EasyMVC;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.MetaInf;
import zero.fluxodecaixa.model.Transacao;
import zero.utils.test.DBUnitTest;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class TestControllerFactory extends ControllerFactory {
    private String[] datasetFileNames;

    public TestControllerFactory(Properties props, String[] datasetFileNames) {
        super(props);

        this.datasetFileNames = datasetFileNames;
    }

    @Override
    public EasyMVC createAndSetupController() throws Exception {
        EasyMVC controller = super.createAndSetupController();

        recreateStructure();

        if (datasetFileNames != null) {
            DBUnitTest dbUnitTest = new DBUnitTest(connectionManager.getConnectionString(), connectionManager.getDriverClassName());

            dbUnitTest.initializeDBUnit(datasetFileNames);
        }

        return controller;
    }

    private void recreateStructure() throws SQLException {
        ConnectionSource source = connectionManager.getConnection();

        TableUtils.dropTable(source, Conta.class, true);
        TableUtils.dropTable(source, Transacao.class, true);
        TableUtils.dropTable(source, Lancamento.class, true);
        TableUtils.dropTable(source, MetaInf.class, true);
        TableUtils.createTable(source, Conta.class);
        TableUtils.createTable(source, Transacao.class);
        TableUtils.createTable(source, Lancamento.class);
        TableUtils.createTable(source, MetaInf.class);
    }

}
