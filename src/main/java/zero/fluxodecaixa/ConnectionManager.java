package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.easymvc.DependencyManager;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class ConnectionManager implements DependencyManager {

    public static final String CONNECTION_STRING = "jdbc:sqlite:./dbunit/test_database";
    public static final String DRIVER_CLASS = "org.sqlite.JDBC";

    private ConnectionSource connectionSource;

    public ConnectionManager() throws SQLException {
        connectionSource = new JdbcConnectionSource(CONNECTION_STRING);
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
