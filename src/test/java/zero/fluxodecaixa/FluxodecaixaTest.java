package zero.fluxodecaixa;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.junit.Before;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import zero.easymvc.DependencyManager;
import zero.easymvc.EasyMVC;
import zero.utils.test.DBUnitTest;

public class FluxodecaixaTest {

    protected EasyMVC controller;
    private String datasetFileName;

    public FluxodecaixaTest() {
        this(null);
    }

    public FluxodecaixaTest(String datasetFileName) {
        super();

        this.datasetFileName = datasetFileName;
    }

    @Before
    public void before() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        setupLogger();

        controller = new EasyMVC();

        TestConnectionManager connectionManager = new TestConnectionManager();

        DependencyManager daoManager = new DaoManager(connectionManager.getConnection());

        controller.addDependencyManager(daoManager);

        if (datasetFileName != null) {
            DBUnitTest dbUnitTest = new DBUnitTest(connectionManager.getConnectionString(), connectionManager.getDriverClassName());

            dbUnitTest.initializeDBUnit(datasetFileName);
        }
    }

    private void setupLogger() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.OFF);
    }

}
