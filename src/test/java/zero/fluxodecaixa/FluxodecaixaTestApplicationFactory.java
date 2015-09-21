package zero.fluxodecaixa;

import zero.easymvc.ormlite.DatabaseVersion;
import zero.utils.test.TestApplicationFactory;
import zero.utils.test.DBUnitUpdater;

import com.j256.ormlite.support.ConnectionSource;

public class FluxodecaixaTestApplicationFactory extends FluxodecaixaApplicationFactory implements TestApplicationFactory {

    private String[] datasetFileNames;

    public FluxodecaixaTestApplicationFactory() {
        super(FluxodecaixaApplicationFactory.BASENAME + "_test");

    }

    @Override
    public void setDatabaseFileNames(String[] datasetFileNames) {
        this.datasetFileNames = datasetFileNames;
    }

    @Override
    protected DatabaseVersion createDatabaseVersion() throws Exception {
        ConnectionSource connection = connectionManager.getConnection();

        FluxodecaixaTestUpdater previousVersion = new FluxodecaixaTestUpdater(connection, super.createDatabaseVersion());

        return new DBUnitUpdater(connection, previousVersion, datasetFileNames);
    }

}
