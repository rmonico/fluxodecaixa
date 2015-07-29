package zero.fluxodecaixa.app;

import zero.easymvc.Parameter;
import zero.fluxodecaixa.model.Conta;

public class ContaAddBean {

    @Parameter
    private String nome;
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

}
