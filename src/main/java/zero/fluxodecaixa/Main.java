package zero.fluxodecaixa;

import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import zero.easymvc.EasyMVC;
import zero.easymvc.EasyMVCException;
import zero.easymvc.StringArrayCommand;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Main {

    public static void main(String[] args) throws SQLException, EasyMVCException {
        Main main = new Main();

        main.run(args);
    }

    private void run(String[] args) throws SQLException, EasyMVCException {
        setupLogger();

        ControllerFactory factory = new ControllerFactory();

        EasyMVC controller = factory.createAndSetupController();

        controller.run(new StringArrayCommand(args));
    }

    private void setupLogger() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.OFF);
    }

}
