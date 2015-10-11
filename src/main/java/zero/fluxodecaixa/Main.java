package zero.fluxodecaixa;

import zero.easymvc.EasyMVC;
import zero.easymvc.StringArrayCommand;
import zero.easymvc.ormlite.factory.AbstractApplicationFactory;

public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.run(args);
    }

    private void run(String[] args) throws Exception {
        AbstractApplicationFactory factory = new FluxodecaixaApplicationFactory();

        factory.makeProperties();

        factory.makeLogger();

        factory.makeConnectionManager();

        factory.makeDaoManager();

        EasyMVC controller = factory.makeController();

        factory.checkAndUpdateDatabaseVersion();

        controller.run(new StringArrayCommand(args));
    }

}
