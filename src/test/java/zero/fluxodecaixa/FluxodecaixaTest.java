package zero.fluxodecaixa;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;

import zero.easymvc.EasyMVC;
import zero.utils.test.DBUnitTest;

public class FluxodecaixaTest {

    protected EasyMVC controller;
    private String datasetFileName;
    protected TestConnectionManager connectionManager;

    public FluxodecaixaTest() {
        this(null);
    }

    public FluxodecaixaTest(String datasetFileName) {
        super();

        this.datasetFileName = datasetFileName;
    }

    @Before
    public void initializeDatabase() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        controller = new EasyMVC();

        connectionManager = new TestConnectionManager();

        controller.addDependencyManager(connectionManager);

        if (datasetFileName != null) {
            DBUnitTest dbUnitTest = new DBUnitTest(TestConnectionManager.getConnectionString(), TestConnectionManager.getDriverClassName());

            dbUnitTest.initializeDBUnit(datasetFileName);
        }
    }

}
