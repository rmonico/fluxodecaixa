package zero.fluxodecaixa;

import java.util.Properties;

import zero.easymvc.EasyMVC;
import zero.easymvc.ormlite.ControllerFactory;
import zero.fluxodecaixa.app.ContaCreateCommand;
import zero.fluxodecaixa.app.ContaListCommand;
import zero.fluxodecaixa.app.ContaRemoveCommand;
import zero.fluxodecaixa.app.HelpCommand;
import zero.fluxodecaixa.app.LancamentoCreateCommand;
import zero.fluxodecaixa.app.LancamentoListCommand;
import zero.fluxodecaixa.app.LancamentoListRenderer;
import zero.fluxodecaixa.app.SaldoCommand;
import zero.fluxodecaixa.app.VersionCommand;
import zero.fluxodecaixa.renderer.ContaCreateRenderer;
import zero.fluxodecaixa.renderer.ContaListRenderer;
import zero.fluxodecaixa.renderer.ContaRemoveRenderer;
import zero.fluxodecaixa.renderer.HelpRenderer;
import zero.fluxodecaixa.renderer.LancamentoCreateRenderer;
import zero.fluxodecaixa.renderer.SaldoRenderer;

public class FluxodecaixaControllerFactory extends ControllerFactory {

    public FluxodecaixaControllerFactory(Properties props) {
        super(props);
    }

    @Override
    public void registerCommandsAndRenderers(EasyMVC controller) {
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
        controller.registerCommandHandler(VersionCommand.class);
        controller.registerRenderer(VersionCommand.class);
        controller.registerCommandHandler(SaldoCommand.class);
        controller.registerRenderer(SaldoRenderer.class);
        controller.registerCommandHandler(HelpCommand.class);
        controller.registerRenderer(HelpRenderer.class);
    }

}
