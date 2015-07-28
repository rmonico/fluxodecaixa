package zero.fluxodecaixa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ContaLsTest {

    private static final String CONNECTION_STRING = "jdbc:sqlite:./dbunit/test_database";
    private static final String DRIVER_CLASS = "org.sqlite.JDBC";

    private IDatabaseConnection getConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class.forName(DRIVER_CLASS);
        Connection jdbcConnection = DriverManager.getConnection(CONNECTION_STRING);

        return new DatabaseConnection(jdbcConnection);
    }

    private IDataSet getDataSet() throws DataSetException, FileNotFoundException {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("dbunit/dbunit_dataset.xml"));
    }

    @Before
    public void initializeDatabase() throws ClassNotFoundException, FileNotFoundException, SQLException, DatabaseUnitException {
        recreateStructure();
        startDBUnit();
    }

    private void recreateStructure() throws SQLException {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(CONNECTION_STRING);

        TableUtils.dropTable(connectionSource, Conta.class, true);
        TableUtils.createTable(connectionSource, Conta.class);
    }

    public void startDBUnit() throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException {
        IDatabaseConnection connection = getConnection();

        IDataSet dataSet = getDataSet();

        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    @Test
    public void doNothing() {

    }
}
