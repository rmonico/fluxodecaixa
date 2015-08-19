package zero.fluxodecaixa.app;

import zero.easymvc.TokenParameter;
import zero.easymvc.PositionalParameter;

public class ContaCreateArguments {

    @PositionalParameter
    private String nome;

    @TokenParameter(token = { "-o", "--observacao" })
    private String observacao;

    @TokenParameter(token = { "-c", "--contabilizavel" })
    private boolean contabilizavel;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isContabilizavel() {
        return contabilizavel;
    }

    public void setContabilizavel(boolean contabilizavel) {
        this.contabilizavel = contabilizavel;
    }

}
