package zero.fluxodecaixa;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

public class ContaLsTest {

    private static final String CONNECTION_STRING = "jdbc:sqlite:./dbunit/database";
    private static final String DRIVER_CLASS = "org.sqlite.JDBC";

    private IDatabaseConnection getConnection() throws Exception {
        Class.forName(DRIVER_CLASS);
        Connection jdbcConnection = DriverManager.getConnection(CONNECTION_STRING);

        return new DatabaseConnection(jdbcConnection);
    }

    private IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("dbunit/dbunit_dataset.xml"));
    }

    @Before
    public void startDBUnit() throws Exception {
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
