package zero.fluxodecaixa.model;

import java.math.BigDecimal;
import java.util.Calendar;

public class Saldo {
    private Calendar data;
    private Conta conta;
    private BigDecimal valor;

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
