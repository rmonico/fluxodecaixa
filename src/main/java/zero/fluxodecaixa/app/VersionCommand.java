package zero.fluxodecaixa.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Renderer;

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

        String majorVersion = props.getProperty("major_version");
        String minorVersion = props.getProperty("minor_version");
        String projectPhase = props.getProperty("project_phase");

        version = new AppVersion(majorVersion, minorVersion, projectPhase);
    }

    @Renderer(path = { "version" })
    public void render() {
        System.out.println(String.format("Fluxo de Caixa version %s.%s%s", version.getMajor(), version.getMinor(), version.getPhase()));
    }
}
