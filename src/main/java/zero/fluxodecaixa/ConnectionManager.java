package zero.fluxodecaixa;

import java.sql.SQLException;
import java.util.Properties;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class ConnectionManager {

    private ConnectionSource connectionSource;
    private Properties props;

    public ConnectionManager(Properties props) throws SQLException {
        this.props = props;
        connectionSource = new JdbcConnectionSource(getConnectionString());
    }

    public String getConnectionString() {
        String jdbcUrl = props.getProperty("jdbc_url");

        if (jdbcUrl != null)
            return jdbcUrl;

        String database = props.getProperty("database");

        if (database != null)
            return String.format("jdbc:sqlite:%s", database);

        return "jdbc:sqlite:database";
    }

    public String getDriverClassName() {
        String jdbcDriverClass = props.getProperty("jdbc_driver_class");

        if (jdbcDriverClass != null)
            return jdbcDriverClass;

        return "org.sqlite.JDBC";
    }

    public ConnectionSource getConnection() {
        return connectionSource;
    }

}
