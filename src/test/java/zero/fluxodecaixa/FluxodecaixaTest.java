package zero.fluxodecaixa;

import java.util.Properties;

import org.junit.Before;

import zero.easymvc.EasyMVC;
import zero.easymvc.ormlite.ControllerFactory;
import zero.easymvc.ormlite.TestControllerFactory;

public class FluxodecaixaTest {

    protected EasyMVC controller;
    private String[] datasetFileNames;
    ControllerFactory controllerFactory;

    public FluxodecaixaTest() {
        this((String[]) null);
    }

    public FluxodecaixaTest(String... datasetFileNames) {
        this.datasetFileNames = datasetFileNames;
    }

    @Before
    public void before() throws Exception {
        Properties props = createTestProperties();

        controllerFactory = new TestControllerFactory(props, datasetFileNames);

        controller = controllerFactory.createAndSetupController(new FluxoDeCaixaDaoManager());
    }

    private static Properties createTestProperties() {
        Properties props = new Properties();

        props.setProperty("jdbc_url", "jdbc:sqlite:dbunit/test_database");
        props.setProperty("jdbc_driver_class", "org.sqlite.JDBC");

        return props;
    }

}
