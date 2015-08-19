package zero.fluxodecaixa.renderer;

import zero.easymvc.Renderer;
import zero.fluxodecaixa.model.Lancamento;
import zero.listprinter.DoubleFormatter;
import zero.utils.TimeUtils;

public class LancamentoCreateRenderer {

    private Lancamento lancamento;

    @Renderer(path = { "lanc", "add" })
    public void render() {
        String data = TimeUtils.dateToString(lancamento.getTransacao().getData());
        int transacaoId = lancamento.getTransacao().getId();
        String origem = lancamento.getOrigem().getNome();
        String destino = lancamento.getDestino().getNome();
        String valor = DoubleFormatter.getInstance().format(lancamento.getValor());
        String observacao = lancamento.getObservacao() != null ? String.format(" (%s)", lancamento.getObservacao()) : "";

        String message = String.format("OK: %s %s -> %s %s%s [transacao: #%d]", data, origem, destino, valor, observacao, transacaoId);

        System.out.println(message);
    }
}
