package zero.fluxodecaixa;

import java.util.Properties;

import org.junit.Before;

import zero.easymvc.EasyMVC;

public class FluxodecaixaTest {

    protected EasyMVC controller;
    private String datasetFileName;

    public FluxodecaixaTest() {
        this(null);
    }

    public FluxodecaixaTest(String datasetFileName) {
        super();

        this.datasetFileName = datasetFileName;
    }

    @Before
    public void before() throws Exception {
        Properties props = createTestProperties();

        ControllerFactory factory = new TestControllerFactory(props, datasetFileName);

        controller = factory.createAndSetupController();
    }

    private static Properties createTestProperties() {
        Properties props = new Properties();

        props.setProperty("jdbc_url", "jdbc:sqlite:dbunit/test_database");
        props.setProperty("jdbc_driver_class", "org.sqlite.JDBC");

        return props;
    }

}
