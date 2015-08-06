package zero.fluxodecaixa;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;

import zero.easymvc.DependencyManager;
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
    public void before() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        controller = new EasyMVC();

        connectionManager = new TestConnectionManager();

        DependencyManager daoManager = new DaoManager(connectionManager.getConnection());
        controller.addDependencyManager(daoManager);

        if (datasetFileName != null) {
            DBUnitTest dbUnitTest = new DBUnitTest(connectionManager.getConnectionString(), connectionManager.getDriverClassName());

            dbUnitTest.initializeDBUnit(datasetFileName);
        }
    }

}
