package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.easymvc.DependencyManager;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class ConnectionManager implements DependencyManager {

    private ConnectionSource connectionSource;

    public ConnectionManager() throws SQLException {
        connectionSource = new JdbcConnectionSource(getConnectionString());
    }

    protected String getConnectionString() {
        return "jdbc:sqlite:./dbunit/database";
    }

    @Override
    public Class<?> dependencyClass() {
        return ConnectionSource.class;
    }

    @Override
    public void beforeUse() {
    }

    @Override
    public ConnectionSource getInstance() {
        return connectionSource;
    }

    @Override
    public void afterUse() throws SQLException {
        connectionSource.close();
    }

}
