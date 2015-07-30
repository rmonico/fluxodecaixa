package zero.fluxodecaixa.app;

import zero.easymvc.PositionalParameter;

public class ContaRemoveArguments {
    @PositionalParameter
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
