package zero.fluxodecaixa;

import java.util.Properties;

import zero.easymvc.ormlite.DatabaseVersion;
import zero.utils.test.DBUnitUpdater;

import com.j256.ormlite.support.ConnectionSource;

public class FluxodecaixaTestApplicationFactory extends FluxodecaixaApplicationFactory {

    private String[] datasetFileNames;

    public FluxodecaixaTestApplicationFactory(Properties props, String[] datasetFileNames) {
        super(props);
        this.datasetFileNames = datasetFileNames;
    }

    @Override
    public DatabaseVersion createDatabaseVersion() throws Exception {
        ConnectionSource connection = connectionManager.getConnection();

        DBUnitUpdater updater = new DBUnitUpdater(connection, new FluxodecaixaTestUpdater(connection, super.createDatabaseVersion()));

        updater.setConnectionString(connectionManager.getConnectionString());

        updater.setJDBCDriverClass(connectionManager.getDriverClassName());

        updater.setDatasetFileNames(datasetFileNames);

        return updater;
    }

}
