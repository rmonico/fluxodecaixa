package zero.fluxodecaixa;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;

import com.j256.ormlite.support.ConnectionSource;

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

        controller.addDependencyManager(connectionManager);

        DependencyManager daoManager = new DaoManager((ConnectionSource) connectionManager.getInstance(ConnectionSource.class));
        controller.addDependencyManager(daoManager);

        if (datasetFileName != null) {
            DBUnitTest dbUnitTest = new DBUnitTest(connectionManager.getConnectionString(), connectionManager.getDriverClassName());

            dbUnitTest.initializeDBUnit(datasetFileName);
        }
    }

}
