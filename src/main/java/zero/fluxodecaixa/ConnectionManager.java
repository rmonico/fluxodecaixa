package zero.fluxodecaixa;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class ConnectionManager {

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

    public ConnectionSource getConnection() {
        return connectionSource;
    }

}
