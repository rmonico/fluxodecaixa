package zero.fluxodecaixa;

import org.junit.Before;

import zero.easymvc.EasyMVC;

public class FluxodecaixaTest {

    private FluxodecaixaTestApplicationFactory factory;
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
        factory = new FluxodecaixaTestApplicationFactory(datasetFileNames);

        factory.makeProperties();

        factory.makeLogger();

        factory.makeConnectionManager();

        controller = factory.makeController();

        factory.checkAndUpdateDatabaseVersion();
    }

}
