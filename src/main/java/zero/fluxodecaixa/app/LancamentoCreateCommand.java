package zero.fluxodecaixa.app;

import zero.easymvc.ArgumentBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.fluxodecaixa.model.Lancamento;

public class LancamentoCreateCommand {

    @ArgumentBean
    private LancamentoCreateArguments args;

    @Bean
    private Lancamento lancamento;

    @CommandHandler(path = { "lanc", "add" })
    public void run() {

    }
}
