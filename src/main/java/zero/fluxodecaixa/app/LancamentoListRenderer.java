package zero.fluxodecaixa.app;

import java.util.List;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Lancamento;

public class LancamentoListRenderer {

    private List<Lancamento> lancamentos;

    @Renderer(path = { "lanc", "ls" })
    public void render() {

    }
}
