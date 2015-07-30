package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.easymvc.EasyMVC;
import zero.easymvc.EasyMVCException;
import zero.easymvc.StringArrayCommand;
import zero.fluxodecaixa.app.ContaLsCommand;
import zero.fluxodecaixa.renderer.ContaLsRenderer;

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

        controller.addDependencyManager(new ConnectionManager());
        controller.registerCommandHandler(ContaLsCommand.class);
        controller.registerRenderer(ContaLsRenderer.class, new StringArrayCommand("conta", "ls"));
    }
}
