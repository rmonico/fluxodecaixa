package zero.fluxodecaixa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import zero.easymvc.EasyMVC;
import zero.easymvc.StringArrayCommand;

public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.run(args);
    }

    private void run(String[] args) throws Exception {
        Properties props = loadConfigsFromFile();

        ControllerFactory factory = new ControllerFactory(props);

        EasyMVC controller = factory.createAndSetupController();

        controller.run(new StringArrayCommand(args));
    }

    private Properties loadConfigsFromFile() throws FileNotFoundException, IOException {
        Properties props = new Properties();

        File file = new File("configs");

        InputStream inStream = new FileInputStream(file);

        props.load(inStream);

        inStream.close();

        return props;
    }
}
