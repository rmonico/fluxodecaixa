package zero.fluxodecaixa.app;

import java.util.Calendar;

import zero.easymvc.DateParser;
import zero.easymvc.PositionalParameter;
import zero.easymvc.TokenParameter;

public class LancamentoCreateArguments {
    @PositionalParameter
    private String nomeOrigem;

    @PositionalParameter(after = "nomeOrigem")
    private String nomeDestino;

    @PositionalParameter(after = "nomeDestino")
    private Double valor;

    @PositionalParameter(after = "valor", required = false)
    private String observacao;

    @TokenParameter(token = { "-d", "--data" }, parser = DateParser.class)
    private Calendar data;

    @TokenParameter(token = { "-t", "--transacao-id" })
    private Integer transacaoId;

    public String getNomeOrigem() {
        return nomeOrigem;
    }

    public void setNomeOrigem(String nomeOrigem) {
        this.nomeOrigem = nomeOrigem;
    }

    public String getNomeDestino() {
        return nomeDestino;
    }

    public void setNomeDestino(String nomeDestino) {
        this.nomeDestino = nomeDestino;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Integer getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(Integer transacaoId) {
        this.transacaoId = transacaoId;
    }

}
