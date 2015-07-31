package zero.fluxodecaixa;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;

import zero.easymvc.EasyMVC;
import zero.utils.test.DBUnitTest;

public class FluxodecaixaTest {

    protected EasyMVC controller;
    protected TestConnectionManager connectionManager;

    @Before
    public void initializeDatabase() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        controller = new EasyMVC();

        connectionManager = new TestConnectionManager();

        controller.addDependencyManager(connectionManager);

        String datasetFileName = getDatasetFileName();

        if (datasetFileName != null) {
            DBUnitTest dbUnitTest = new DBUnitTest(datasetFileName, connectionManager.getConnectionString(), connectionManager.getDriverClassName());

            dbUnitTest.initializeDBUnit();
        }
    }

    protected String getDatasetFileName() {
        return null;
    }

}
