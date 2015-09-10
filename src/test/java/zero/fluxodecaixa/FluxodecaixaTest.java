package zero.fluxodecaixa;

import java.util.Properties;

import org.junit.Before;

import zero.easymvc.EasyMVC;
import zero.easymvc.ormlite.ControllerFactory;
import zero.easymvc.ormlite.LoggerFactory;
import zero.easymvc.ormlite.test.TestControllerFactory;
import zero.fluxodecaixa.app.ContaCreateCommand;
import zero.fluxodecaixa.app.ContaListCommand;
import zero.fluxodecaixa.app.ContaRemoveCommand;
import zero.fluxodecaixa.app.HelpCommand;
import zero.fluxodecaixa.app.LancamentoCreateCommand;
import zero.fluxodecaixa.app.LancamentoListCommand;
import zero.fluxodecaixa.app.LancamentoListRenderer;
import zero.fluxodecaixa.app.SaldoCommand;
import zero.fluxodecaixa.app.VersionCommand;
import zero.fluxodecaixa.renderer.ContaCreateRenderer;
import zero.fluxodecaixa.renderer.ContaListRenderer;
import zero.fluxodecaixa.renderer.ContaRemoveRenderer;
import zero.fluxodecaixa.renderer.HelpRenderer;
import zero.fluxodecaixa.renderer.LancamentoCreateRenderer;
import zero.fluxodecaixa.renderer.SaldoRenderer;

public class FluxodecaixaTest {

    protected EasyMVC controller;
    private String[] datasetFileNames;
    ControllerFactory controllerFactory;
    private static final Class<?>[] commandList = new Class<?>[] { ContaCreateCommand.class, ContaListCommand.class, ContaRemoveCommand.class, HelpCommand.class, LancamentoCreateCommand.class, LancamentoListCommand.class, SaldoCommand.class, VersionCommand.class };
    private static final Class<?>[] rendererList = new Class<?>[] { ContaCreateRenderer.class, ContaListRenderer.class, ContaRemoveRenderer.class, HelpRenderer.class, LancamentoCreateRenderer.class, LancamentoListRenderer.class, SaldoRenderer.class, VersionCommand.class };

    public FluxodecaixaTest() {
        this((String[]) null);
    }

    public FluxodecaixaTest(String... datasetFileNames) {
        this.datasetFileNames = datasetFileNames;
    }

    @Before
    public void before() throws Exception {
        Properties props = createTestProperties();

        LoggerFactory loggerFactory = new LoggerFactory(props);

        loggerFactory.setup();

        controllerFactory = new TestControllerFactory(props, datasetFileNames);

        controllerFactory.setCommandList(commandList);

        controllerFactory.setRendererList(rendererList);

        controllerFactory.setDaoManager(new FluxoDeCaixaDaoManager());

        controller = controllerFactory.createAndSetupController();
    }

    private static Properties createTestProperties() {
        Properties props = new Properties();

        props.setProperty("jdbc_url", "jdbc:sqlite:dbunit/test_database");
        props.setProperty("jdbc_driver_class", "org.sqlite.JDBC");

        return props;
    }

}
