package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.easymvc.DependencyManager;
import zero.easymvc.EasyMVC;
import zero.easymvc.EasyMVCException;
import zero.easymvc.StringArrayCommand;
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

import com.j256.ormlite.support.ConnectionSource;

public class Main {

    // TODO Create a switch to control log verbosity
    public static void main(String[] args) throws SQLException, EasyMVCException {
        Main main = new Main();

        main.run(args);
    }

    private EasyMVC controller;

    private void run(String[] args) throws SQLException, EasyMVCException {
        createAndSetupController();

        controller.run(new StringArrayCommand(args));
    }

    private void createAndSetupController() throws SQLException {
        controller = new EasyMVC();

        ConnectionManager connectionManager = new ConnectionManager();

        controller.addDependencyManager(connectionManager);

        DependencyManager daoManager = new DaoManager((ConnectionSource) connectionManager.getInstance(ConnectionSource.class));
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
    }
}
