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

    public String getConnectionString() {
        return "jdbc:sqlite:./dbunit/database";
    }

    public String getDriverClassName() {
        return "org.sqlite.JDBC";
    }

    @Override
    public Class<?>[] managedClasses() {
        return new Class<?>[] { ConnectionSource.class };
    }

    @Override
    public Object getInstance(Class<?> dependencyClass) throws SQLException {
        return connectionSource;
    }

    @Override
    public void afterUse(Class<?> dependencyClass) throws SQLException {
        connectionSource.close();
    }

}
