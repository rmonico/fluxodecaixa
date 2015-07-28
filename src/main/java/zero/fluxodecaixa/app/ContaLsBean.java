package zero.fluxodecaixa.app;

import java.util.LinkedList;
import java.util.List;

import zero.fluxodecaixa.model.Conta;

public class ContaLsBean {
    private List<Conta> contas;

    public ContaLsBean() {
        contas = new LinkedList<Conta>();
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

}
