package zero.utils.test;

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

public class DBUnitTest {

    private String datasetFile;
    private String jdbcDriverClassName;
    private String connectionString;

    public DBUnitTest(String datasetFile, String connectionString, String driverClassName) {
        this.datasetFile = datasetFile;
        this.connectionString = connectionString;
        this.jdbcDriverClassName = driverClassName;
    }

    @Before
    public void initializeDBUnit() throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException {
        IDatabaseConnection connection = getDBUnitConnection();

        IDataSet dataSet = getDataSet(datasetFile);

        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    private IDatabaseConnection getDBUnitConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class.forName(jdbcDriverClassName);

        Connection jdbcConnection = DriverManager.getConnection(connectionString);

        return new DatabaseConnection(jdbcConnection);
    }

    private IDataSet getDataSet(String datasetFile) throws DataSetException, FileNotFoundException {
        return new FlatXmlDataSetBuilder().build(new FileInputStream(datasetFile));
    }

}
