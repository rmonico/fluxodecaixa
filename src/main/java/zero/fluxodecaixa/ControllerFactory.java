package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.easymvc.DependencyManager;
import zero.easymvc.EasyMVC;
import zero.fluxodecaixa.app.ContaCreateCommand;
import zero.fluxodecaixa.app.ContaListCommand;
import zero.fluxodecaixa.app.ContaRemoveCommand;
import zero.fluxodecaixa.app.LancamentoCreateCommand;
import zero.fluxodecaixa.app.LancamentoListCommand;
import zero.fluxodecaixa.app.LancamentoListRenderer;
import zero.fluxodecaixa.renderer.ContaCreateRenderer;
import zero.fluxodecaixa.renderer.ContaListRenderer;
import zero.fluxodecaixa.renderer.ContaRemoveRenderer;
import zero.fluxodecaixa.renderer.LancamentoCreateRenderer;

public class ControllerFactory {
    private EasyMVC controller;

    public EasyMVC createAndSetupController() throws SQLException {
        controller = new EasyMVC();

        ConnectionManager connectionManager = new ConnectionManager();

        DependencyManager daoManager = new DaoManager(connectionManager.getConnection());

        controller.addDependencyManager(daoManager);

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

        return controller;
    }
}