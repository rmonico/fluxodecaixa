package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.easymvc.DependencyManager;
import zero.fluxodecaixa.app.dao.AbstractDao;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;

import com.j256.ormlite.dao.DaoManager;
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
        return new Class<?>[] { ConnectionSource.class, ContaDao.class, TransacaoDao.class, LancamentoDao.class };
    }

    @Override
    public Object getInstance(Class<?> dependencyClass) throws SQLException {
        if (dependencyClass.isAssignableFrom(AbstractDao.class))
            return DaoManager.createDao(connectionSource, dependencyClass);
        else
            return connectionSource;
    }

    @Override
    public void afterUse(Class<?> dependencyClass) throws SQLException {
        connectionSource.close();
    }

}
