package zero.fluxodecaixa.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Renderer;
import zero.fluxodecaixa.FluxodecaixaApplicationFactory;

public class VersionCommand {

    @Bean
    private AppVersion version;

    @CommandHandler(path = { "version" })
    public void execute() throws IOException {
        Properties props = new Properties();

        // TODO Pegar das propriedades da aplicação

        File file = new File("version.properties");

        InputStream fis = new FileInputStream(file);

        props.load(fis);

        fis.close();

        String majorVersion = props.getProperty(FluxodecaixaApplicationFactory.EXECUTABLE_MAJOR_VERSION_PROPERTY_KEY);
        String minorVersion = props.getProperty(FluxodecaixaApplicationFactory.EXECUTABLE_MINOR_VERSION_PROPERTY_KEY);
        String projectPhase = props.getProperty(FluxodecaixaApplicationFactory.EXECUTABLE_PROJECT_PHASE_PROPERTY_KEY);

        version = new AppVersion(majorVersion, minorVersion, projectPhase);
    }

    @Renderer(path = { "version" })
    public void render() {
        System.out.println(String.format("Fluxo de Caixa version %s.%s%s", version.getMajor(), version.getMinor(), version.getPhase()));
    }
}
