package zero.fluxodecaixa;

import java.util.Properties;

import org.junit.Before;

import zero.easymvc.EasyMVC;

public class FluxodecaixaTest {

    protected EasyMVC controller;
    private String[] datasetFileNames;

    public FluxodecaixaTest() {
        this((String[]) null);
    }

    public FluxodecaixaTest(String... datasetFileNames) {
        this.datasetFileNames = datasetFileNames;
    }

    @Before
    public void before() throws Exception {
        Properties props = createTestProperties();

        FluxodecaixaTestApplicationFactory factory = new FluxodecaixaTestApplicationFactory(props, datasetFileNames);

        factory.makeLogger();

        factory.makeConnectionManager();

        controller = factory.makeController();

        factory.makeDatabaseUpdater();
    }

    private static Properties createTestProperties() {
        Properties props = new Properties();

        props.setProperty("jdbc_url", "jdbc:sqlite:dbunit/test_database");
        props.setProperty("jdbc_driver_class", "org.sqlite.JDBC");

        return props;
    }

}
