package zero.fluxodecaixa;

import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import zero.easymvc.DependencyManager;
import zero.easymvc.EasyMVC;
import zero.fluxodecaixa.app.ContaCreateCommand;
import zero.fluxodecaixa.app.ContaListCommand;
import zero.fluxodecaixa.app.ContaRemoveCommand;
import zero.fluxodecaixa.app.LancamentoCreateCommand;
import zero.fluxodecaixa.app.LancamentoListCommand;
import zero.fluxodecaixa.app.LancamentoListRenderer;
import zero.fluxodecaixa.app.SaldoCommand;
import zero.fluxodecaixa.app.VersionCommand;
import zero.fluxodecaixa.renderer.ContaCreateRenderer;
import zero.fluxodecaixa.renderer.ContaListRenderer;
import zero.fluxodecaixa.renderer.ContaRemoveRenderer;
import zero.fluxodecaixa.renderer.LancamentoCreateRenderer;
import zero.fluxodecaixa.renderer.SaldoRenderer;

public class ControllerFactory {
    private EasyMVC controller;
    protected ConnectionManager connectionManager;
    private Properties props;

    public ControllerFactory(Properties props) {
        this.props = props;
    }

    public EasyMVC createAndSetupController() throws Exception {
        setupLogger();

        controller = new EasyMVC();

        registerDependencies();

        registerCommandsAndRenderers();

        return controller;
    }

    private void registerDependencies() throws SQLException {
        connectionManager = new ConnectionManager(props);

        DependencyManager daoManager = new DaoManager(connectionManager.getConnection());

        controller.addDependencyManager(daoManager);
    }

    private void registerCommandsAndRenderers() {
        controller.registerCommandHandler(ContaCreateCommand.class);
        controller.registerRenderer(ContaCreateRenderer.class);
        controller.registerCommandHandler(ContaListCommand.class);
        controller.registerRenderer(ContaListRenderer.class);
        controller.registerCommandHandler(ContaRemoveCommand.class);
        controller.registerRenderer(ContaRemoveRenderer.class);
        controller.registerCommandHandler(LancamentoCreateCommand.class);
        controller.registerRenderer(LancamentoCreateRenderer.class);
        controller.registerCommandHandler(LancamentoListCommand.class);
        controller.registerRenderer(LancamentoListRenderer.class);
        controller.registerCommandHandler(VersionCommand.class);
        controller.registerRenderer(VersionCommand.class);
        controller.registerCommandHandler(SaldoCommand.class);
        controller.registerRenderer(SaldoRenderer.class);
    }

    private void setupLogger() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        String verbosity = props.getProperty("logger_verbosity");

        Level loggerLevel;

        if (verbosity == null)
            loggerLevel = Level.ERROR;
        else {
            switch (verbosity) {
            case "off":
                loggerLevel = Level.OFF;
                break;

            case "error":
                loggerLevel = Level.ERROR;
                break;

            case "warning":
                loggerLevel = Level.WARN;
                break;

            case "info":
                loggerLevel = Level.INFO;
                break;

            case "trace":
                loggerLevel = Level.TRACE;
                break;

            case "debug":
                loggerLevel = Level.DEBUG;
                break;

            case "all":
                loggerLevel = Level.ALL;
                break;

            default:
                loggerLevel = Level.ERROR;
            }
        }

        root.setLevel(loggerLevel);
    }

}
