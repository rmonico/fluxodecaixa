package zero.utils.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class DBUnitTest {

    @Rule
    public TestName name = new TestName();
    private String jdbcDriverClassName;
    private String connectionString;

    public DBUnitTest(String connectionString, String driverClassName) {
        this.connectionString = connectionString;
        this.jdbcDriverClassName = driverClassName;
    }

    @Before
    public void setup() throws ClassNotFoundException, SQLException, DatabaseUnitException, IOException {
        initializeDBUnit();
    }

    public void initializeDBUnit(String... datasetFileNames) throws ClassNotFoundException, SQLException, DatabaseUnitException, IOException {
        IDatabaseConnection connection = getDBUnitConnection();

        for (String datasetFileName : datasetFileNames) {
            IDataSet dataSet = getDataSet(datasetFileName, name.getMethodName());

            try {
                DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
            } finally {
                connection.close();
            }
        }
    }

    private IDatabaseConnection getDBUnitConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class.forName(jdbcDriverClassName);

        Connection jdbcConnection = DriverManager.getConnection(connectionString);

        return new DatabaseConnection(jdbcConnection);
    }

    protected IDataSet getDataSet(String datasetFileName, String currentTest) throws DataSetException, IOException {
        FileInputStream fis;

        if (datasetFileName == null)
            fis = new FileInputStream(getDatasetFileName(currentTest));
        else
            fis = new FileInputStream(datasetFileName);

        FlatXmlDataSet dataset = new FlatXmlDataSetBuilder().build(fis);

        fis.close();

        return dataset;
    }

    protected String getDatasetFileName(String currentTest) {
        return null;
    }

}
