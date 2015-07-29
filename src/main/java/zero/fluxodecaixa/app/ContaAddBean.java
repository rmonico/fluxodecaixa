package zero.fluxodecaixa.app;

import zero.easymvc.FlagParameter;
import zero.easymvc.PositionalParameter;
import zero.fluxodecaixa.model.Conta;

public class ContaAddBean {

    @PositionalParameter
    private String nome;
    @FlagParameter(token = { "-c", "--contabilizavel" })
    private boolean contabilizavel;

    private Conta conta;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public boolean isContabilizavel() {
        return contabilizavel;
    }

    public void setContabilizavel(boolean contabilizavel) {
        this.contabilizavel = contabilizavel;
    }

}
