package zero.fluxodecaixa;

import zero.easymvc.ormlite.DatabaseVersion;
import zero.utils.test.DBUnitUpdater;

import com.j256.ormlite.support.ConnectionSource;

public class FluxodecaixaTestApplicationFactory extends FluxodecaixaApplicationFactory {

    private String[] datasetFileNames;

    public FluxodecaixaTestApplicationFactory(String[] datasetFileNames) {
        super(FluxodecaixaApplicationFactory.BASENAME + "_test");

        this.datasetFileNames = datasetFileNames;
    }

    @Override
    protected DatabaseVersion createDatabaseVersion() throws Exception {
        ConnectionSource connection = connectionManager.getConnection();

        FluxodecaixaTestUpdater previousVersion = new FluxodecaixaTestUpdater(connection, super.createDatabaseVersion());

        return new DBUnitUpdater(connection, previousVersion, datasetFileNames);
    }

}
