package zero.fluxodecaixa.app;

import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;
import zero.utils.TimeUtils;

public class LancamentoListCommand {

    @Bean
    public List<Lancamento> lancamentos;

    @CommandHandler(path = { "lanc", "ls" })
    public void run() throws ParseException {
        lancamentos = new LinkedList<Lancamento>();

        lancamentos.add(createLancamento(TimeUtils.stringToDate("15/Jun/2015"), "itau", "carteira", 50d, "Dinheiro sacado"));
        lancamentos.add(createLancamento(TimeUtils.stringToDate("15/Jun/2015"), "carteira", "casa", 25.5d, "Dinheiro da feira"));
        lancamentos.add(createLancamento(TimeUtils.stringToDate("15/Jun/2015"), "carteira", "casa", 10d, "Pão"));
    }

    private Lancamento createLancamento(Calendar data, String nomeOrigem, String nomeDestino, double valor, String observacao) {
        Lancamento lancamento = new Lancamento();
        lancamento.setTransacao(new Transacao());
        lancamento.getTransacao().setData(data);

        Conta origem = new Conta();
        origem.setNome(nomeOrigem);
        lancamento.setOrigem(origem);

        Conta destino = new Conta();
        destino.setNome(nomeDestino);
        lancamento.setDestino(destino);

        lancamento.setValor(valor);

        lancamento.setObservacao(observacao);
        return lancamento;
    }
}
