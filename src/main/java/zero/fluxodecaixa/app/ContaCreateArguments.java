package zero.fluxodecaixa.app;

import zero.easymvc.FlagParameter;
import zero.easymvc.PositionalParameter;

public class ContaCreateArguments {

    @PositionalParameter
    private String nome;
    @FlagParameter(token = { "-c", "--contabilizavel" })
    private boolean contabilizavel;

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

}
