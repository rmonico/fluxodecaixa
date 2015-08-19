package zero.fluxodecaixa.app;

import zero.easymvc.TokenParameter;
import zero.easymvc.PositionalParameter;

public class ContaCreateArguments {

    @PositionalParameter
    private String nome;

    @TokenParameter(token = { "-c", "--contabilizavel" })
    private boolean contabilizavel;

    @TokenParameter(token = { "-s", "--saldo" })
    private boolean saldo;

    @TokenParameter(token = { "-o", "--observacao" })
    private String observacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isContabilizavel() {
        return contabilizavel;
    }

    public void setContabilizavel(boolean contabilizavel) {
        this.contabilizavel = contabilizavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isSaldo() {
        return saldo;
    }

    public void setSaldo(boolean saldo) {
        this.saldo = saldo;
    }

}
