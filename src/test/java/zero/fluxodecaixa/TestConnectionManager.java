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

import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class TestConnectionManager extends ConnectionManager {

    private static final String DRIVER_CLASS = "org.sqlite.JDBC";

    public TestConnectionManager() throws SQLException {
        super();

        recreateStructure();
    }

    @Override
    protected String getConnectionString() {
        return "jdbc:sqlite:./dbunit/test_database";
    }

    private void recreateStructure() throws SQLException {
        ConnectionSource source = getInstance();

        TableUtils.dropTable(source, Conta.class, true);
        TableUtils.createTable(source, Conta.class);
    }

    public void initializeDBUnitDataset(String datasetFile) throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException {
        IDatabaseConnection connection = getDBUnitConnection();

        IDataSet dataSet = getDataSet(datasetFile);

        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    private IDatabaseConnection getDBUnitConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class.forName(DRIVER_CLASS);

        Connection jdbcConnection = DriverManager.getConnection(getConnectionString());

        return new DatabaseConnection(jdbcConnection);
    }

    private IDataSet getDataSet(String datasetFile) throws DataSetException, FileNotFoundException {
        return new FlatXmlDataSetBuilder().build(new FileInputStream(datasetFile));
    }

}
