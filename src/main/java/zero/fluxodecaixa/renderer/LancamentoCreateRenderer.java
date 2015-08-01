package zero.fluxodecaixa.renderer;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Lancamento;

public class LancamentoCreateRenderer {

    private Lancamento lancamento;

    @Renderer(path = { "lanc", "add" })
    public void render() {
        String origem = lancamento.getOrigem().getNome();
        String destino = lancamento.getDestino().getNome();
        double valor = lancamento.getValor();

        String message = String.format("Lançamento \"%s -> %s (%f)\" criado com sucesso.", origem, destino, valor);

        System.out.println(message);
    }
}
