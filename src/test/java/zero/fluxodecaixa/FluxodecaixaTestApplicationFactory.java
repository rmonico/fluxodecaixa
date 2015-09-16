package zero.fluxodecaixa;

import zero.easymvc.ormlite.DatabaseVersion;
import zero.easymvc.ormlite.factory.ApplicationFactory;
import zero.utils.test.DBUnitUpdater;

import com.j256.ormlite.support.ConnectionSource;

public class FluxodecaixaTestApplicationFactory extends FluxodecaixaApplicationFactory {

    public static final String DEFAULT_TEST_JDBC_URL = "jdbc:sqlite:dbunit/test_database";
    public static final String DEFAULT_TEST_JDBC_DRIVER_CLASS = "org.sqlite.JDBC";

    private String[] datasetFileNames;

    public FluxodecaixaTestApplicationFactory(String[] datasetFileNames) {
        this.datasetFileNames = datasetFileNames;
    }

    @Override
    protected void setupDefaultProperties() {
        super.setupDefaultProperties();

        properties.setProperty(ApplicationFactory.JDBC_URL_KEY, DEFAULT_TEST_JDBC_URL);
        properties.setProperty(ApplicationFactory.JDBC_DRIVER_CLASS_KEY, DEFAULT_TEST_JDBC_DRIVER_CLASS);
    }

    @Override
    protected DatabaseVersion createDatabaseVersion() throws Exception {
        ConnectionSource connection = connectionManager.getConnection();

        FluxodecaixaTestUpdater previousVersion = new FluxodecaixaTestUpdater(connection, super.createDatabaseVersion());

        DBUnitUpdater updater = createDBUnitUpdater(previousVersion, datasetFileNames);

        return updater;
    }

}
