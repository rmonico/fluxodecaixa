package zero.fluxodecaixa;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;

import zero.easymvc.EasyMVC;

public class DatabaseTest {

    protected EasyMVC controller;
    protected TestConnectionManager connectionManager;

    @Before
    public void initializeDatabase() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        controller = new EasyMVC();

        connectionManager = new TestConnectionManager();

        controller.addDependencyManager(connectionManager);

        String datasetFileName = getDatasetFileName();

        if (datasetFileName != null)
            connectionManager.initializeDBUnitDataset(datasetFileName);
    }

    protected String getDatasetFileName() {
        return null;
    }

}
