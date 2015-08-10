package zero.fluxodecaixa.app;

import zero.easymvc.CommandHandler;
import zero.easymvc.Renderer;

public class VersionCommand {

    private static final String MAJOR_VERSION = "0";
    private static final String MINOR_VERSION = "1";
    private static final String STAGE = " - beta";

    @CommandHandler(path = { "version" })
    public void execute() {
    }

    @Renderer(path = { "version" })
    public void render() {
        System.out.println(String.format("Fluxo de Caixa version %s.%s%s", MAJOR_VERSION, MINOR_VERSION, STAGE));
    }
}
