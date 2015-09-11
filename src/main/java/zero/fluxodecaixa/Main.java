package zero.fluxodecaixa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import zero.easymvc.EasyMVC;
import zero.easymvc.StringArrayCommand;
import zero.easymvc.ormlite.ConnectionManager;
import zero.easymvc.ormlite.factory.AbstractApplicationFactory;

public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.run(args);
    }

    private void run(String[] args) throws Exception {
        Properties props = loadConfigsFromFile();

        AbstractApplicationFactory factory = new FluxodecaixaApplicationFactory(props);

        factory.makeLogger();

        factory.makeConnectionManager();

        EasyMVC controller = factory.makeController();

        factory.checkAndUpdateDatabaseVersion();

        controller.run(new StringArrayCommand(args));
    }

    private Properties loadConfigsFromFile() throws FileNotFoundException, IOException {
        Properties props = new Properties();

        File file = new File(getConfigFileName());

        if (!file.exists())
            createDefaultConfigFile(file);

        InputStream inStream = new FileInputStream(file);

        props.load(inStream);

        inStream.close();

        return props;
    }

    private String getConfigFileName() {
        return ConnectionManager.getFluxodecaixaHome() + "config.properties";
    }

    private void createDefaultConfigFile(File file) throws IOException {
        file.getParentFile().mkdirs();

        file.createNewFile();

        FileOutputStream stream = new FileOutputStream(file);

        Properties props = new Properties();

        props.setProperty("jdbc_driver_class", ConnectionManager.DEFAULT_JDBC_DRIVER);
        props.setProperty("jdbc_url", ConnectionManager.getDefaultDatabaseURL());

        props.store(stream, null);

        stream.close();
    }

}
