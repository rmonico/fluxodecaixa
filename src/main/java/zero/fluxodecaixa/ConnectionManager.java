package zero.fluxodecaixa;

import java.sql.SQLException;
import java.util.Properties;

import zero.fluxodecaixa.app.dao.MetaInfDao;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.MetaInf;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

public class ConnectionManager {

    public static final String DEFAULT_JDBC_DRIVER = "org.sqlite.JDBC";
    public static final String APP_INTERNAL_VERSION = "1";

    private ConnectionSource connectionSource;
    private Properties props;

    public ConnectionManager(Properties props) throws SQLException {
        this.props = props;

        connectionSource = new JdbcConnectionSource(getConnectionString());

        updateDatabaseStructure();
    }

    protected String getConnectionString() {
        String jdbcUrl = props.getProperty("jdbc_url");

        if (jdbcUrl != null)
            return jdbcUrl;

        String database = props.getProperty("database");

        if (database != null)
            return getDefaultDatabaseURL(database);

        return getDefaultDatabaseURL(getDefaultDatabaseFileName());
    }

    public static String getDefaultDatabaseURL() {
        return getDefaultDatabaseURL(getDefaultDatabaseFileName());
    }

    private static String getDefaultDatabaseURL(String database) {
        return String.format("jdbc:sqlite:%s", database);
    }

    private static String getDefaultDatabaseFileName() {
        return getFluxodecaixaHome() + "database.sqlite";
    }

    protected String getDriverClassName() {
        String jdbcDriverClass = props.getProperty("jdbc_driver_class");

        if (jdbcDriverClass != null)
            return jdbcDriverClass;

        return DEFAULT_JDBC_DRIVER;
    }

    public ConnectionSource getConnection() {
        return connectionSource;
    }

    // FIXME Its not a good place to put this.... May be a good idea create a
    // Defaults class to this and other static stuff of this and Main classes
    static String getFluxodecaixaHome() {
        String fluxodecaixahome = System.getenv("FLUXODECAIXA_HOME");

        if (fluxodecaixahome == null)
            fluxodecaixahome = System.getenv("HOME") + "/.config/fluxodecaixa/";

        return fluxodecaixahome;
    }

    private void updateDatabaseStructure() throws SQLException {
        if (!metainfTableExists())
            updateToVersion0_1();

        MetaInfDao dao = MetaInfDao.getInstance(connectionSource);

        MetaInf appVersion = dao.getValue(MetaInfDao.APP_INTERNAL_VERSION_KEY);

        boolean appVersionInDatabase = appVersion != null;

        if (!appVersionInDatabase) {
            appVersion = new MetaInf();

            appVersion.setKey(MetaInfDao.APP_INTERNAL_VERSION_KEY);
        }

        appVersion.setValue(APP_INTERNAL_VERSION);

        if (appVersionInDatabase)
            dao.update(appVersion);
        else
            dao.create(appVersion);
    }

    private boolean metainfTableExists() throws SQLException {
        DatabaseConnection connection = connectionSource.getReadWriteConnection();

        return connection.isTableExists(MetaInf.TABLE_NAME);
    }

    private void updateToVersion0_1() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Conta.class);
        TableUtils.createTableIfNotExists(connectionSource, Transacao.class);
        TableUtils.createTableIfNotExists(connectionSource, Lancamento.class);
        TableUtils.createTableIfNotExists(connectionSource, MetaInf.class);
    }

}
