package zero.fluxodecaixa.app;

import java.util.Calendar;

import zero.easymvc.TokenParameter;
import zero.easymvc.PositionalParameter;

public class LancamentoCreateArguments {
    @PositionalParameter
    private String nomeOrigem;

    @PositionalParameter(after = "nomeOrigem")
    private String nomeDestino;

    @PositionalParameter(after = "nomeDestino")
    private Double valor;

    @PositionalParameter(after = "valor", required = false)
    private String observacao;

    @TokenParameter(token = { "-d", "--data" })
    private Calendar data;

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

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
