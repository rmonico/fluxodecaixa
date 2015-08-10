package zero.fluxodecaixa;

import java.sql.SQLException;
import java.util.Properties;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class ConnectionManager {

    public static final String DEFAULT_JDBC_DRIVER = "org.sqlite.JDBC";

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
            return getDefaultDatabaseURL(database);

        return getDefaultDatabaseURL(getDefaultDatabaseFileName());
    }

    public String getDriverClassName() {
        String jdbcDriverClass = props.getProperty("jdbc_driver_class");

        if (jdbcDriverClass != null)
            return jdbcDriverClass;

        return DEFAULT_JDBC_DRIVER;
    }

    public ConnectionSource getConnection() {
        return connectionSource;
    }

    public static String getDefaultDatabaseURL() {
        return getDefaultDatabaseURL(getDefaultDatabaseFileName());
    }

    private static String getDefaultDatabaseFileName() {
        return getFluxodecaixaHome() + "database.sqlite";
    }

    // FIXME Its not a good place to put this.... May be a good idea create a
    // Defaults class to this and other static stuff of this and Main classes
    static String getFluxodecaixaHome() {
        String fluxodecaixahome = System.getenv("FLUXODECAIXA_HOME");

        if (fluxodecaixahome == null)
            fluxodecaixahome = System.getenv("HOME") + "/.config/fluxodecaixa/";

        return fluxodecaixahome;
    }

    public static String getDefaultDatabaseURL(String database) {
        return String.format("jdbc:sqlite:%s", database);
    }

}
