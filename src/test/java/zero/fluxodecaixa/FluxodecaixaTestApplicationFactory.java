package zero.fluxodecaixa;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import zero.easymvc.ormlite.ConnectionManager;
import zero.easymvc.ormlite.DatabaseUpdater;
import zero.easymvc.ormlite.MetaInfUpdater;
import zero.fluxodecaixa.database.DatabaseVersion_1;
import zero.utils.test.AbstractTestApplicationFactory;
import zero.utils.test.TestApplicationFactory;

import com.j256.ormlite.support.ConnectionSource;

public class FluxodecaixaTestApplicationFactory extends FluxodecaixaApplicationFactory implements TestApplicationFactory {

    private AbstractTestApplicationFactory testApplicationFactoryDelegated;

    public FluxodecaixaTestApplicationFactory() {
        this(AbstractTestApplicationFactory.DATABASE_LAST_VERSION);
    }

    public FluxodecaixaTestApplicationFactory(int databaseVersion) {
        super(FluxodecaixaApplicationFactory.BASENAME + "_test");

        testApplicationFactoryDelegated = new AbstractTestApplicationFactory(databaseVersion);
    }

    @Override
    public ConnectionManager makeConnectionManager() throws SQLException {
        ConnectionManager connectionManager = super.makeConnectionManager();

        ConnectionSource connection = connectionManager.getConnection();

        List<DatabaseUpdater> updaters = new LinkedList<DatabaseUpdater>();

        updaters.add(new MetaInfUpdater(connection));
        updaters.add(new DatabaseVersion_1(connection));

        testApplicationFactoryDelegated.addUpdaters(updaters);

        return connectionManager;
    }

    @Override
    public DatabaseUpdater getDatabaseUpdater() {
        return testApplicationFactoryDelegated.getDatabaseUpdater();
    }

    @Override
    public DatabaseUpdater getBeforeTestsDatabaseUpdater() {
        return testApplicationFactoryDelegated.getBeforeTestsDatabaseUpdater();
    }

}
